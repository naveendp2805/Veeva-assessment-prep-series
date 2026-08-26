-- ============================================================
-- VEEVA PRACTICE
-- SQL QUESTION 2 : CUSTOMERS + ORDERS
-- ============================================================
--
-- Customers:
--   custid
--   name
--   city
--
-- Orders:
--   orderid
--   custid
--   orderDate
--   orderAmount
--
-- Relationship:
--   Customers.custid = Orders.custid
-- ============================================================


-- ============================================================
-- STEP 1: CREATE CUSTOMERS TABLE
-- ============================================================
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Orders;

CREATE TABLE Customers (
                           custid INT,
                           name VARCHAR(100),
                           city VARCHAR(100)
);


-- ============================================================
-- STEP 2: INSERT CUSTOMERS
-- ============================================================

INSERT INTO Customers (custid, name, city)
VALUES
    (1, 'Ravi', 'Hyderabad'),
    (2, 'Naveen', 'Chennai'),
    (3, 'Arun', 'Bangalore'),
    (4, 'Kiran', 'Mumbai'),
    (5, 'Rahul', 'Hyderabad');


-- ============================================================
-- STEP 3: CREATE ORDERS TABLE
-- ============================================================

CREATE TABLE Orders (
                        orderid INT,
                        custid INT,
                        orderDate DATE,
                        orderAmount INT
);


-- ============================================================
-- STEP 4: INSERT ORDERS
-- ============================================================

INSERT INTO Orders (orderid, custid, orderDate, orderAmount)
VALUES
    (101, 1, '2023-05-09', 500),
    (102, 1, '2023-05-10', 700),
    (103, 1, '2023-06-05', 800),

    (104, 2, '2023-05-11', 1000),
    (105, 2, '2023-06-15', 600),
    (106, 2, '2023-07-04', 900),

    (107, 3, '2023-05-12', 250),
    (108, 3, '2023-05-20', 400),
    (109, 3, '2023-07-06', 1200),

    (110, 4, '2023-07-04', 2000),

    (111, 5, '2023-07-06', 300),
    (112, 5, '2023-07-10', 500);


-- ============================================================
-- QUESTION 2(i)
-- Find the total number of orders placed by each customer,
-- excluding the orders placed in June.
-- ============================================================

SELECT
    c.name,
    COUNT(o.orderid) AS total_orders
FROM Customers c
         LEFT JOIN Orders o
                   ON c.custid = o.custid
                       AND EXTRACT(MONTH FROM o.orderDate) <> 6
GROUP BY c.name
ORDER BY c.name;


-- ============================================================
-- QUESTION 2(ii)
-- Find the customer who has placed the highest
-- total order value.
-- ============================================================

SELECT
    c.name,
    SUM(o.orderAmount) AS total_order_value
FROM Customers c
         JOIN Orders o
              ON c.custid = o.custid
GROUP BY c.custid, c.name
ORDER BY total_order_value DESC
LIMIT 1;


-- ============================================================
-- QUESTION 2(iii)
-- List all orders placed on 2023-07-04 and 2023-07-06.
-- ============================================================

SELECT
    *
FROM Orders
WHERE orderDate IN ('2023-07-04', '2023-07-06')
ORDER BY orderDate;


-- ============================================================
-- QUESTION 2(iv)
-- Find the average order value for each city.
-- ============================================================

SELECT
    c.city,
    AVG(o.orderAmount) AS average_order_value
FROM Customers c
         JOIN Orders o
              ON c.custid = o.custid
GROUP BY c.city
ORDER BY c.city;


-- ============================================================
-- QUESTION 2(v)
-- Identify customers who have not placed any orders.
-- ============================================================

SELECT
    c.custid,
    c.name,
    c.city
FROM Customers c
         LEFT JOIN Orders o
                   ON c.custid = o.custid
WHERE o.orderid IS NULL;


-- ============================================================
-- QUESTION 2(vi)
-- Find the month with the highest total order value.
-- ============================================================

SELECT
    DATE_TRUNC('month', orderDate) AS month,
    SUM(orderAmount) AS total_order_value
FROM Orders
GROUP BY DATE_TRUNC('month', orderDate)
ORDER BY total_order_value DESC
LIMIT 1;


-- ============================================================
-- QUESTION 2(vii)
-- Display the top 2 customers with the most orders
-- in the last 30 days.
-- ============================================================

SELECT
    c.custid,
    c.name,
    COUNT(o.orderid) AS total_orders
FROM Customers c
         JOIN Orders o
              ON c.custid = o.custid
WHERE o.orderDate >= CURRENT_DATE - INTERVAL '30 days'
GROUP BY c.custid, c.name
ORDER BY total_orders DESC
LIMIT 2;


-- ============================================================
-- QUESTION 2(viii)
-- List all orders placed on 2023-07-04 and 2023-07-06
-- along with their corresponding customer names.
-- ============================================================

SELECT
    o.orderid,
    o.custid,
    c.name,
    o.orderDate,
    o.orderAmount
FROM Orders o
         JOIN Customers c
              ON o.custid = c.custid
WHERE o.orderDate IN ('2023-07-04', '2023-07-06')
ORDER BY o.orderDate, o.orderid;