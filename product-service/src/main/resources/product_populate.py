import json
import mysql.connector
import requests
import decimal
from datetime import datetime

def connect_to_database(host, database, user, password):
    connection = mysql.connector.connect(
        host=host,
        database=database,
        user=user,
        password=password
    )
    return connection

def get_or_create(cursor, table, unique_field, value):
    cursor.execute(f"SELECT id FROM {table} WHERE {unique_field} = %s", (value,))
    result = cursor.fetchone()
    if result:
        return result[0]
    cursor.execute(f"INSERT INTO {table} ({unique_field}) VALUES (%s)", (value,))
    return cursor.lastrowid

def get_or_create_tag(cursor, tag_name):
    return get_or_create(cursor, "tags", "name", tag_name)

def get_or_create_category(cursor, category_name):
    return get_or_create(cursor, "categories", "name", category_name)

def product_exists(cursor, sku):
    cursor.execute("SELECT id FROM products WHERE sku = %s", (sku,))
    return cursor.fetchone() is not None

def fetch_products():
    url = "https://dummyjson.com/products?limit=0"
    response = requests.get(url)
    response.raise_for_status()
    data = response.json()["products"]
    return data

def main():
    products = fetch_products()
    conn = connect_to_database("localhost", "ecommerce_productdb", "root", "drowssap")
    cursor = conn.cursor()

    for product in products:
        sku = product.get("sku")
        if product_exists(cursor, sku):
            print(f"Product with SKU {sku} already exists. Skipping.")
            continue

        # Category
        category_id = get_or_create_category(cursor, product.get("category", ""))

        # Insert product
        cursor.execute("""
            INSERT INTO products (
                title, description, price, discount_percentage, rating, stock, sku, weight,
                warranty_information, shipping_information, availability_status, return_policy, minimum_order_quantity,
                barcode, qr_code, thumbnail, brand, category_id,
                width, height, depth
            ) VALUES (
                %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s
            )
        """, (
            product.get("title"),
            product.get("description"),
            decimal.Decimal(str(product.get("price", 0))),
            product.get("discountPercentage"),
            product.get("rating"),
            product.get("stock"),
            sku,
            product.get("weight"),
            product.get("warrantyInformation"),
            product.get("shippingInformation"),
            product.get("availabilityStatus"),
            product.get("returnPolicy"),
            product.get("minimumOrderQuantity"),
            product.get("meta", {}).get("barcode"),
            product.get("meta", {}).get("qrCode"),
            product.get("thumbnail"),
            product.get("brand"),
            category_id,
            product.get("dimensions", {}).get("width"),
            product.get("dimensions", {}).get("height"),
            product.get("dimensions", {}).get("depth")
        ))

        product_id = cursor.lastrowid

        # Images
        for img_url in product.get("images", []):
            cursor.execute(
                "INSERT INTO product_images (url, product_id) VALUES (%s, %s)",
                (img_url, product_id)
            )

        # Reviews
        for review in product.get("reviews", []):
            # Convert ISO 8601 date to MySQL DATETIME format
            date_str = review.get("date")
            mysql_date = None
            if date_str:
                try:
                    # Remove 'Z' and parse
                    dt = datetime.fromisoformat(date_str.replace('Z', '+00:00'))
                    mysql_date = dt.strftime('%Y-%m-%d %H:%M:%S')
                except Exception as e:
                    print(f"Error parsing date '{date_str}': {e}")
            cursor.execute(
                """INSERT INTO product_reviews (rating, comment, date, reviewer_name, reviewer_email, product_id)
                   VALUES (%s, %s, %s, %s, %s, %s)""",
                (
                    review.get("rating"),
                    review.get("comment"),
                    mysql_date,
                    review.get("reviewerName"),
                    review.get("reviewerEmail"),
                    product_id
                )
            )

        # Tags (many-to-many)
        for tag in product.get("tags", []):
            tag_id = get_or_create_tag(cursor, tag)
            cursor.execute(
                "INSERT IGNORE INTO product_tags (product_id, tag_id) VALUES (%s, %s)",
                (product_id, tag_id)
            )

    conn.commit()
    cursor.close()
    conn.close()
    print("Product data population complete.")

if __name__ == "__main__":
    main() 