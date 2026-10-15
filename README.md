# Banking System - Data Structures & Algorithms

**HND in Computing - Data Structures and Algorithms Module**

A comprehensive Banking System built with **Java Swing** that demonstrates the practical application of core **Data Structures and Algorithms**. This project was developed as part of the **Higher National Diploma (HND) in Computing** - **Data Structures and Algorithms** module assignment.

---

##  Module Information

| Detail | Information |
|--------|-------------|
| **Module** | Data Structures and Algorithms |
| **Course** | HND in Computing |
| **Student** | Alex Abishan |
| **GitHub** | [@AlexAbishan11](https://github.com/AlexAbishan11) |

---

##  Features

###  Account Management
- Create new bank accounts (auto-generated account numbers)
- Update account details (Name, NIC, Phone)
- Toggle account status (Active / Deactivated)
- NIC duplicate validation
- Undo last account operation

###  Transactions
- Deposit money
- Withdraw money
- Transfer money between accounts
- VIP Priority transactions
- Insufficient balance validation

###  History & Queue
- View all account history
- Transaction queue with VIP priority
- Process queue (individual or all)
- Undo last transaction

---

##  Data Structures Used

| Data Structure | Usage in Project |
|----------------|------------------|
| **ArrayList** | Store all bank accounts dynamically |
| **Stack** | Undo functionality (account state history + transaction undo) |
| **Queue (LinkedList)** | Transaction history display (FIFO) |
| **PriorityQueue** | VIP transactions get priority over normal transactions |
| **HashMap** | (If used in code) |

---

##  Technologies Used

| Technology | Purpose |
|------------|---------|
| Java | Core language |
| Java Swing | GUI Framework |
| NetBeans IDE | Development environment |
| OOP Principles | Encapsulation, Inheritance, Polymorphism |
| Collections Framework | ArrayList, Stack, Queue, PriorityQueue |

---


## 🚀 Setup Instructions

### Prerequisites
- Java JDK 8 or higher
- NetBeans IDE

### Steps to Run

1. **Clone or download** this repository
2. **Open NetBeans IDE**
3. **File → Open Project** → Select `BankSystem` folder
4. **Right-click project** → **Run**
5. Login UI will open

### Login Credentials
- **Username:** `Alex`
- **Password:** `1234`

---

##  Key Features Explained

### 1. Priority Queue for VIP Transactions
Transactions are added to a `PriorityQueue`. VIP transactions are processed **before** normal transactions. Within the same priority, **Last Come First Serve (LCFS)** is applied using timestamps.

### 2. Stack for Undo
Every account modification saves a **snapshot** to a `Stack`. The undo operation restores the previous state.

### 3. Queue for Transaction History
A `Queue` stores transaction records in FIFO order for display.

### 4. ArrayList for Account Storage
All accounts are stored in an `ArrayList` for dynamic resizing.

---


##  Author

**Alex Abishan**
- GitHub: [@AlexAbishan11](https://github.com/AlexAbishan11)
- Course: HND in Computing
- Module: Data Structures and Algorithms

---

