DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE ACCOUNT (ACCOUNT_NO LONG PRIMARY KEY AUTO_INCREMENT NOT NULL,
CUSTOMER_NAME VARCHAR(30),
BALANCE DECIMAL(19,4)
);

INSERT INTO ACCOUNT (ACCOUNT_NO, CUSTOMER_NAME, BALANCE) VALUES (0, 'Karen', 200.00);


CREATE TABLE TRANSACTION_HIST (TIME_STAMP TIMESTAMP NOT NULL, 
ACCOUNT_FROM LONG NOT NULL,
ACCOUNT_TO LONG NOT NULL,
VALUE DECIMAL(19,4)
);