/*
    DAY 01 - SQL
    QUESTION 2: SALESMEN AND CUSTOMERS

    Problem:
    1. Find the number of customers handled by each salesman,
       where the salesman and customer belong to the same city.

    2. Find the salesmen who are NOT living in the same city as
       the customers named 'James', 'Green', or 'John'.
*/


-- ============================================
-- CREATE TABLES
-- ============================================

DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Salesman;

CREATE TABLE Salesman (
    sid INT PRIMARY KEY,
    name VARCHAR(100),
    city VARCHAR(100)
);

CREATE TABLE Customer (
    cid INT PRIMARY KEY,
    name VARCHAR(100),
    city VARCHAR(100)
);


-- ============================================
-- INSERT SAMPLE DATA
-- ============================================

INSERT INTO Salesman (sid, name, city)
VALUES
(1, 'James', 'New York'),
(2, 'David', 'London'),
(3, 'Robert', 'Paris'),
(4, 'John', 'New York');

INSERT INTO Customer (cid, name, city)
VALUES
(101, 'Alice', 'New York'),
(102, 'Bob', 'London'),
(103, 'Charlie', 'Paris'),
(104, 'Daniel', 'New York'),
(105, 'Emma', 'Tokyo');


-- ============================================
-- QUESTION 1
-- ============================================
-- Find the number of customers handled by
-- each salesman from the same city.


SELECT
    s.name,
    COUNT(*) AS "Customers Count"
FROM Salesman s
JOIN Customer c
    ON s.city = c.city
GROUP BY s.name;


-- ============================================
-- QUESTION 2
-- ============================================
-- Find salesmen who are NOT living in the same
-- city as customers named James, Green, or John.


SELECT
    s.name
FROM Salesman s
WHERE s.city NOT IN (
    SELECT c.city
    FROM Customer c
    WHERE c.name IN ('James', 'Green', 'John')
);