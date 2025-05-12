-- Create customer table
CREATE TABLE customer (
    cust_no VARCHAR2(10) PRIMARY KEY,
    name VARCHAR2(50),
    phoneno VARCHAR2(15),
    city VARCHAR2(30)
);

-- Create branch table
CREATE TABLE branch (
    branch_code VARCHAR2(10) PRIMARY KEY,
    branch_name VARCHAR2(50),
    branch_city VARCHAR2(30)
);

-- Create account table
CREATE TABLE account (
    account_no VARCHAR2(10) PRIMARY KEY,
    cust_no VARCHAR2(10),
    type VARCHAR2(10),
    balance NUMBER(12,2),
    branch_code VARCHAR2(10),
    FOREIGN KEY (cust_no) REFERENCES customer(cust_no),
    FOREIGN KEY (branch_code) REFERENCES branch(branch_code)
);

-- Create loan table
CREATE TABLE loan (
    loan_no VARCHAR2(10) PRIMARY KEY,
    cust_no VARCHAR2(10),
    amount NUMBER(12,2),
    branch_code VARCHAR2(10),
    FOREIGN KEY (cust_no) REFERENCES customer(cust_no),
    FOREIGN KEY (branch_code) REFERENCES branch(branch_code)
);

-- Insert some sample data
INSERT INTO customer VALUES ('C001', 'ANWESHA DAS', '9999999999', 'BHUB');
INSERT INTO customer VALUES ('C002', 'SACHIN SINGH', '9898989898', 'CTC');
INSERT INTO customer VALUES ('C003', 'ARJUN MISHRA', '7777777777', 'BBSR');

INSERT INTO branch VALUES ('B01', 'Main Branch', 'BHUB');
INSERT INTO branch VALUES ('B02', 'City Branch', 'CTC');

INSERT INTO account VALUES ('A001', 'C001', 'Saving', 5000, 'B01');
INSERT INTO account VALUES ('A002', 'C002', 'Current', 10000, 'B02');

INSERT INTO loan VALUES ('L001', 'C001', 20000, 'B01');
INSERT INTO loan VALUES ('L002', 'C002', 15000, 'B02');

COMMIT;
