import requests
import mysql.connector

ROLE_MAP = {
    "admin": 1,
    "moderator": 2,
    "user": 3
}
# -----------------------

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


def main():
    users = fetch_users()
    # print(users)
    conn = connect_to_database("localhost", "ecommerce_userdb", "root", "drowssap")
    cursor = conn.cursor()

    for user in users:
        user_id = user["id"]
        username = user["username"]
        email = user["email"]
        password = user["password"]
        role = user.get("role", "user")
        role_id = ROLE_MAP.get(role, 3)
        print(user)
        # Insert into users
        cursor.execute("""
            INSERT INTO users (id, username, email, password, role_id)
            VALUES (%s, %s, %s, %s, %s)
            """, (user_id, username, email, password, role_id))

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

    conn.commit()
    cursor.close()
    conn.close()
    print("Database population complete.")


if __name__ == "__main__":
    main()
