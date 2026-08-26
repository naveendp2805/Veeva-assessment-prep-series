-- ============================================================
-- VEEVA PRACTICE - SQL QUESTION 3
-- ============================================================
--
-- QUESTION:
--
-- Consider the following Transactions table.
--
-- Identify payments made by the same merchant using the same
-- credit card with the same amount within 10 minutes of each
-- other.
--
-- Count the repeated payments as well.
--
--
-- TABLE COLUMNS:
--
-- transaction_id          : Unique transaction ID
-- merchant_id             : ID of the merchant
-- creditcard_id           : ID of the credit card
-- amount                  : Payment amount
-- transaction_timestamp   : Date and time of payment
--
--
-- A payment is considered repeated when:
--
-- 1. Same merchant
-- 2. Same credit card
-- 3. Same amount
-- 4. Transaction time is within 10 minutes
--
-- ============================================================


-- ============================================================
-- STEP 1: DROP TABLE
--
-- This allows us to execute the complete file multiple times
-- without getting "table already exists" error.
-- ============================================================

DROP TABLE IF EXISTS Transactions;


-- ============================================================
-- STEP 2: CREATE TABLE
-- ============================================================

CREATE TABLE Transactions (
                              transaction_id INT,
                              merchant_id INT,
                              creditcard_id INT,
                              amount DECIMAL(10,2),
                              transaction_timestamp TIMESTAMP
);


-- ============================================================
-- STEP 3: INSERT SAMPLE DATA
-- ============================================================

INSERT INTO Transactions
(transaction_id, merchant_id, creditcard_id, amount, transaction_timestamp)
VALUES
    (1, 101, 501, 500.00, '2023-05-10 10:00:00'),
    (2, 101, 501, 500.00, '2023-05-10 10:05:00'),
    (3, 101, 501, 500.00, '2023-05-10 10:20:00'),
    (4, 101, 502, 500.00, '2023-05-10 10:06:00'),
    (5, 102, 501, 500.00, '2023-05-10 10:07:00'),
    (6, 101, 501, 700.00, '2023-05-10 10:08:00'),
    (7, 101, 501, 500.00, '2023-05-10 10:08:00');


-- ============================================================
-- STEP 4: DISPLAY ALL TRANSACTIONS
-- ============================================================

SELECT *
FROM Transactions
ORDER BY transaction_timestamp;


-- ============================================================
-- QUESTION:
--
-- Find the number of repeated payments.
--
-- We use a SELF JOIN because we need to compare one
-- transaction with another transaction from the same table.
--
-- t1 and t2 represent two different transactions.
--
-- Conditions:
--
-- t1.merchant_id = t2.merchant_id
--     -> Same merchant
--
-- t1.creditcard_id = t2.creditcard_id
--     -> Same credit card
--
-- t1.amount = t2.amount
--     -> Same payment amount
--
-- t1.transaction_id <> t2.transaction_id
--     -> They must be different transactions
--
-- ABS(EXTRACT(EPOCH FROM
--     (t1.transaction_timestamp - t2.transaction_timestamp)))
-- <= 600
--
-- 600 seconds = 10 minutes.
--
-- ============================================================


-- ============================================================
-- SOLUTION 1:
-- Display the repeated transaction pairs
-- ============================================================

SELECT
    t1.transaction_id AS transaction_1,
    t2.transaction_id AS transaction_2,
    t1.merchant_id,
    t1.creditcard_id,
    t1.amount,
    t1.transaction_timestamp AS timestamp_1,
    t2.transaction_timestamp AS timestamp_2
FROM Transactions t1
         JOIN Transactions t2
              ON t1.merchant_id = t2.merchant_id
                  AND t1.creditcard_id = t2.creditcard_id
                  AND t1.amount = t2.amount
                  AND t1.transaction_id < t2.transaction_id
                  AND ABS(
                              EXTRACT(
                                      EPOCH FROM
                                      (t1.transaction_timestamp - t2.transaction_timestamp)
                              )
                      ) <= 600
ORDER BY t1.transaction_id;


-- ============================================================
-- SOLUTION 2:
-- Count the repeated payments.
--
-- EXISTS is used so that each transaction is counted only once,
-- even if it matches multiple other transactions.
--
-- Example:
--
-- Transaction 1 matches transaction 2 and transaction 7.
-- We still count transaction 1 only ONCE.
-- ============================================================

SELECT
    COUNT(*) AS repeated_payment_count
FROM Transactions t1
WHERE EXISTS (
    SELECT 1
    FROM Transactions t2
    WHERE t1.transaction_id <> t2.transaction_id
      AND t1.merchant_id = t2.merchant_id
      AND t1.creditcard_id = t2.creditcard_id
      AND t1.amount = t2.amount
      AND ABS(
                  EXTRACT(
                          EPOCH FROM
                          (t1.transaction_timestamp - t2.transaction_timestamp)
                  )
          ) <= 600
);


-- ============================================================
-- EXPECTED RESULT
-- ============================================================
--
-- repeated_payment_count
-- ----------------------
-- 3
--
--
-- Why 3?
--
-- Transaction 1 -> Transaction 2
-- 10:00 -> 10:05 = 5 minutes
-- Same merchant, card and amount
--
-- Transaction 1 -> Transaction 7
-- 10:00 -> 10:08 = 8 minutes
-- Same merchant, card and amount
--
-- Transaction 2 -> Transaction 7
-- 10:05 -> 10:08 = 3 minutes
-- Same merchant, card and amount
--
-- Therefore the repeated transaction PAIRS are:
--
-- (1, 2)
-- (1, 7)
-- (2, 7)
--
-- ============================================================


-- ============================================================
-- IMPORTANT:
--
-- We use:
--
--     t1.transaction_id < t2.transaction_id
--
-- in SOLUTION 1.
--
-- Without this condition, we would get:
--
--     (1,2)
--     (2,1)
--
-- which represents the same pair twice.
--
-- ============================================================


-- ============================================================
-- POSTGRESQL TIMESTAMP CALCULATION
--
-- PostgreSQL can subtract two TIMESTAMP values:
--
--     timestamp1 - timestamp2
--
-- This gives an INTERVAL.
--
-- EXTRACT(EPOCH FROM interval)
--
-- converts that interval into seconds.
--
-- 10 minutes = 10 * 60 = 600 seconds.
--
-- Therefore:
--
--     ABS(EXTRACT(EPOCH FROM (...))) <= 600
--
-- means the two transactions occurred within 10 minutes
-- of each other.
-- ============================================================