# 💳 Banking Management System using Java & Oracle SQL

This is a console-based **Banking Management System** developed using **Java (JDBC)** and **Oracle SQL**. It allows you to manage customer records, bank accounts, and loans through a command-line interface.

---

## 📌 Features

- Show all customer records
- Add, update, and delete customer information
- View account and loan details
- Deposit and withdraw money from accounts

---

## 🛠 Technologies Used

- **Java** (JDBC API)
- **Oracle Database 11g**
- SQL (DDL & DML)
- BufferedReader for input handling

---

## 🧩 Database Schema

### 1. `customer`
| Column     | Type         | Description           |
|------------|--------------|-----------------------|
| cust_no    | VARCHAR2(10) | Primary key           |
| name       | VARCHAR2(50) | Customer's name       |
| phoneno    | VARCHAR2(15) | Customer's phone no.  |
| city       | VARCHAR2(30) | Customer's city       |

### 2. `branch`
| Column       | Type         | Description           |
|--------------|--------------|-----------------------|
| branch_code  | VARCHAR2(10) | Primary key           |
| branch_name  | VARCHAR2(50) | Branch name           |
| branch_city  | VARCHAR2(30) | Location              |

### 3. `account`
| Column       | Type         | Description                  |
|--------------|--------------|------------------------------|
| account_no   | VARCHAR2(10) | Primary key                  |
| cust_no      | VARCHAR2(10) | Foreign key → customer       |
| type         | VARCHAR2(10) | Account type (Saving/Current)|
| balance      | NUMBER(12,2) | Current balance              |
| branch_code  | VARCHAR2(10) | Foreign key → branch         |

### 4. `loan`
| Column       | Type         | Description                  |
|--------------|--------------|------------------------------|
| loan_no      | VARCHAR2(10) | Primary key                  |
| cust_no      | VARCHAR2(10) | Foreign key → customer       |
| amount       | NUMBER(12,2) | Loan amount                  |
| branch_code  | VARCHAR2(10) | Foreign key → branch         |



