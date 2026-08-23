/*
    Day 01 - SQL Question 1

    Problem:
    Given a product table containing product name, price and quantity,
    find the product(s) having the 3rd highest value of:

        price * quantity

    Approach:
    1. Calculate price * quantity for every product.
    2. Rank products based on this value in descending order.
    3. Use DENSE_RANK() to find the 3rd highest distinct value.
*/

-- Create table
DROP TABLE IF EXISTS product;

CREATE TABLE product (
    product_id INT PRIMARY KEY,
    product_name VARCHAR(100),
    price DECIMAL(10,2),
    quantity INT
);

-- Insert sample data
INSERT INTO product (product_id, product_name, price, quantity)
VALUES
(1, 'Laptop', 60000, 2),
(2, 'Mouse', 500, 10),
(3, 'Keyboard', 1500, 5),
(4, 'Monitor', 12000, 3),
(5, 'Phone', 30000, 4),
(6, 'Headphones', 3000, 8);

-- Solution
SELECT
    t.product_name,
    t.pq AS total_value
FROM (
    SELECT
        product_name,
        price * quantity AS pq,
        DENSE_RANK() OVER (
            ORDER BY price * quantity DESC
        ) AS product_rank
    FROM product
) AS t
WHERE t.product_rank = 3;