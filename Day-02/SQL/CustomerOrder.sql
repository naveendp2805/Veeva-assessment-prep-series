-- ============================================================
-- CUSTOMERS + ORDERS
-- ============================================================
--
-- Description:
--
-- We have two tables:
--
-- Customers:
--   custId
--   name
--   city
--
-- Orders:
--   orderId
--   custId
--   orderDate
--   orderAmount
--
-- Question:
-- Find the total number of orders placed by each customer,
-- excluding the orders placed in June.
--
-- Requirements:
-- 1. Join Customers and Orders using custId.
-- 2. Exclude orders placed in June.
-- 3. Display every customer, including customers who have
--    no orders after excluding June.
-- 4. Count the number of qualifying orders for each customer.
--
-- Important:
-- We use LEFT JOIN because customers with zero orders
-- should also appear in the result.
--
-- The June condition is placed inside ON instead of WHERE.
-- This allows LEFT JOIN to retain customers with no
-- matching orders.
-- ============================================================


-- ============================================================
-- STEP 1: CREATE CUSTOMERS TABLE
-- ============================================================
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Orders;

CREATE TABLE public.Customers (
                                  custId INT,
                                  name VARCHAR(100),
                                  city VARCHAR(100)
);


-- ============================================================
-- STEP 2: INSERT CUSTOMERS
-- ============================================================

INSERT INTO public.Customers (custId, name, city)
VALUES
    (1, 'Ravi', 'Hyderabad'),
    (2, 'Naveen', 'Chennai'),
    (3, 'Arun', 'Bangalore'),
    (4, 'Kiran', 'Mumbai');


-- ============================================================
-- STEP 3: CREATE ORDERS TABLE
-- ============================================================

CREATE TABLE public.Orders (
                               orderId INT,
                               custId INT,
                               orderDate DATE,
                               orderAmount INT
);


-- ============================================================
-- STEP 4: INSERT ORDERS
-- ============================================================

INSERT INTO public.Orders (orderId, custId, orderDate, orderAmount)
VALUES
    (101, 1, '2023-05-09', 500),
    (102, 1, '2023-05-10', 700),
    (103, 1, '2023-06-05', 800),
    (104, 2, '2023-05-11', 1000),
    (105, 2, '2023-06-15', 600),
    (106, 3, '2023-05-12', 250),
    (107, 3, '2023-05-20', 400);


-- ============================================================
-- STEP 5: SOLUTION
--
-- Find total number of orders for each customer,
-- excluding June orders.
--
-- LEFT JOIN:
-- Keeps all customers, even if they have no qualifying orders.
--
-- EXTRACT(MONTH FROM o.orderDate):
-- Extracts the month number from orderDate.
--
-- <> 6:
-- Excludes June because June = month 6.
--
-- COUNT(o.orderId):
-- Counts only matching orders.
-- If there are no matching orders, orderId is NULL,
-- so COUNT() returns 0.
-- ============================================================

SELECT
    c.name,
    COUNT(o.orderId) AS total_orders
FROM public.Customers c
LEFT JOIN public.Orders o ON c.custId = o.custId
AND EXTRACT(MONTH FROM o.orderDate) <> 6
GROUP BY c.name
ORDER BY c.name;


-- ============================================================
-- EXPECTED OUTPUT
-- ============================================================
--
-- name    | total_orders
-- --------+-------------
-- Arun    | 2
-- Kiran   | 0
-- Naveen  | 1
-- Ravi    | 2
--
-- June orders are:
--   Order 103 -> Ravi   -> excluded
--   Order 105 -> Naveen -> excluded
--
-- Remaining orders:
--   Ravi   -> 2
--   Naveen -> 1
--   Arun   -> 2
--   Kiran  -> 0
-- ============================================================