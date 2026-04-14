DROP DATABASE IF EXISTS bankdb;

CREATE DATABASE bankdb;
USE bankdb;

CREATE TABLE accounts (
                          account_id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          email VARCHAR(100) UNIQUE,
                          balance DOUBLE NOT NULL DEFAULT 0
);

CREATE TABLE transactions (
                              transaction_id INT AUTO_INCREMENT PRIMARY KEY,
                              account_id INT,
                              type VARCHAR(50),
                              amount DOUBLE NOT NULL,
                              date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE
);

INSERT INTO accounts(name, email, balance)
VALUES
    ('Amit Sharma', 'amit@gmail.com', 30000),
    ('Priya Patil', 'priya@gmail.com', 20000),
    ('Rahul Verma', 'rahul@gmail.com', 15000);

INSERT INTO transactions(account_id, type, amount)
VALUES
    (1, 'DEPOSIT', 30000),
    (1, 'WITHDRAW', 2000),
    (2, 'DEPOSIT', 20000);