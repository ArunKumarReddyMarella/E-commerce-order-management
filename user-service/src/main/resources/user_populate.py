import requests
import mysql.connector
from mysql.connector import Error

# Role definitions with their IDs and descriptions
ROLES = [
    {"id": 1, "name": "admin", "description": "Administrator with full access"},
    {"id": 2, "name": "moderator", "description": "Moderator with limited admin access"},
    {"id": 3, "name": "user", "description": "Regular user account"}
]

# Map role names to their IDs for easy lookup
ROLE_MAP = {role["name"]: role["id"] for role in ROLES}

def connect_to_database(host, database, user, password):
    """Establish a connection to the MySQL database."""
    try:
        connection = mysql.connector.connect(
            host=host,
            database=database,
            user=user,
            password=password
        )
        if connection.is_connected():
            print(f"Connected to the {database} database")
        return connection
    except Error as e:
        print(f"Error: {e}")
        return None

def fetch_users():
    url = "https://dummyjson.com/users?limit=0"
    response = requests.get(url)
    response.raise_for_status()
    return response.json()["users"]


def ensure_roles_exist(cursor):
    """Ensure all required roles exist in the database."""
    try:
        # Check if roles table exists and has data
        cursor.execute("""
            SELECT COUNT(*) FROM information_schema.tables 
            WHERE table_schema = 'ecommerce_userdb' 
            AND table_name = 'roles'
        """)
        table_exists = cursor.fetchone()[0] > 0
        
        if not table_exists:
            print("Roles table does not exist. Please ensure the database is properly set up.")
            return False
            
        # Insert roles that don't exist
        for role in ROLES:
            cursor.execute("SELECT id FROM roles WHERE id = %s", (role["id"],))
            if not cursor.fetchone():
                cursor.execute("""
                    INSERT INTO roles (id, name, description)
                    VALUES (%s, %s, %s)
                """, (role["id"], role["name"], role["description"]))
                print(f"Added role: {role['name']}")
        return True
    except Error as e:
        print(f"Error ensuring roles exist: {e}")
        return False

def main():
    users = fetch_users()
    conn = connect_to_database("localhost", "ecommerce_userdb", "root", "drowssap")
    if not conn:
        return
        
    cursor = conn.cursor()
    
    # Ensure required roles exist before proceeding with user creation
    if not ensure_roles_exist(cursor):
        print("Failed to ensure required roles exist. Exiting.")
        cursor.close()
        conn.close()
        return

    for user in users:
        user_id = user["id"]
        username = user["username"]
        email = user["email"]
        password = user["password"]
        role = user.get("role", "user")
        role_id = ROLE_MAP.get(role, 3)
        print(user)
        # Insert into users
        try:
            cursor.execute("""
                INSERT INTO users (id, username, email, password, role_id)
                VALUES (%s, %s, %s, %s, %s)
                """, (user_id, username, email, password, role_id))
        except mysql.connector.IntegrityError as e:
            if e.errno == 1062:  # Duplicate entry error
                print(f"User {username} already exists, skipping...")
                continue
            else:
                print(f"Error inserting user {username}: {e}")
                continue

        # Insert into user_profiles
        cursor.execute("""
        INSERT INTO user_profiles (
            user_id, first_name, last_name, maiden_name, age, gender, phone, birth_date, image, blood_group, height, weight, eye_color, hair_color, hair_type, ip, mac_address
        ) VALUES (
            %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s
        )
        """, (
            user_id,
            user.get("firstName") or "",
            user.get("lastName") or "",
            user.get("maidenName") or "",
            user.get("age"),
            user.get("gender") or "",
            user.get("phone") or "",
            user.get("birthDate") or "",
            user.get("image") or "",
            user.get("bloodGroup") or "",
            user.get("height"),
            user.get("weight"),
            user.get("eyeColor") or "",
            user.get("hair", {}).get("color") or "",
            user.get("hair", {}).get("type") or "",
            user.get("ip") or "",
            user.get("macAddress") or ""
        ))

        # Insert into addresses
        address = user.get("address", {})
        coordinates = address.get("coordinates", {})
        cursor.execute("""
            INSERT INTO addresses (
                user_id, address, city, state, state_code, postal_code, country, lat, lng
            ) VALUES (
                %s, %s, %s, %s, %s, %s, %s, %s, %s
            )
            """, (
                user_id,
                address.get("address") or "",
                address.get("city") or "",
                address.get("state") or "",
                address.get("stateCode") or "",
                address.get("postalCode") or "",
                address.get("country") or "",
                coordinates.get("lat") or "",
                coordinates.get("lng") or ""
            )) 

        # Insert into banks
        bank = user.get("bank", {})
        cursor.execute("""
            INSERT INTO banks (
                user_id, card_expire, card_number, card_type, currency, iban
            ) VALUES (
                %s, %s, %s, %s, %s, %s
            )
            """, (
            user_id,
            bank.get("cardExpire") or "",
            bank.get("cardNumber") or "",
            bank.get("cardType") or "",
            bank.get("currency") or "",
            bank.get("iban") or ""
        ))

    try:
        conn.commit()
        print("Database population completed successfully!")
    except Error as e:
        print(f"Error committing changes: {e}")
        conn.rollback()
    finally:
        cursor.close()
        conn.close()


if __name__ == "__main__":
    main()
