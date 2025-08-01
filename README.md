<p align="center">
  <img alt="Language: Java" src="https://img.shields.io/badge/language-Java-blue?style=for-the-badge" />
  <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-green?style=for-the-badge" />
</p>

---

# 🛡️ Secure Banking Validator

A polished **Java Swing** desktop app demonstrating the **Chain of Responsibility** pattern for real-time transaction validation.  
Detect format errors, enforce limits, and guard against fraud—all before any money moves!

> **“Validate every transfer. Secure every transaction.”**

---

## ✨ Key Features

- **🔗 Chain of Responsibility**  
  Sequential `FormatValidator → LimitValidator → FraudValidator` handlers ensure each check runs in order.

- **📋 Comprehensive Validation**  
  - **FormatValidator**: Required fields & numeric parsing  
  - **LimitValidator**: Daily transfer cap enforcement  
  - **FraudValidator**: Large-transaction fraud alert

- **🎨 Intuitive GUI**  
  Nimbus L&F, styled text fields, color-themed buttons & result pane.

- **⚡ Instant Feedback**  
  Real-time result messages: ❌ rejection reasons or ✅ approval details.

---

## 🚀 Quick Start

### Prerequisites
- **Java JDK 11+**
- **Maven** (or your IDE’s build tool)
- **Git**

### Installation  
```bash
git clone https://github.com/Tharindu714/Security-Validator-Banking-DialogBox.git
cd Security-Validator-Banking-DialogBox
mvn clean package
````

### Run

```bash
java -jar target/Security-Validator-Banking-DialogBox.jar
```

---

## 📂 Project Structure

```
src/
└── main/
    ├── java/
    │   └── com/tharindu/smartBanking/
    │       ├── Transaction.java
    │       ├── Handler.java
    │       ├── AbstractHandler.java
    │       ├── FormatValidator.java
    │       ├── LimitValidator.java
    │       ├── FraudValidator.java
    │       └── MobileBankingApp.java
    └── resources/
        └── (any GUI images or icons)
```

---

## 🧩 UML Diagrams

<div align="center">
<img width="859" height="744" alt="UML" src="https://github.com/user-attachments/assets/54479121-b7a2-4bda-96cd-eac77760ddcd" />
</div>

> *Add your `.png`/`.svg` diagrams under `docs/uml/`.*

---

## 🎉 Screenshot Showcase

| <img src="https://github.com/user-attachments/assets/31da6cde-7d66-4d96-8d97-3bd8c63c9366" alt="Enter Details" width="400px" /> | <img src="https://github.com/user-attachments/assets/1146de28-544c-4638-a983-12886d0d16aa" alt="Validation in Process" width="400px" /> | <img src="https://github.com/user-attachments/assets/cec14811-41b1-47ef-97e5-1e0c30973e5c" alt="Result Approved/Rejected" width="400px" /> |
| :-------------------------------------------------------------------: | :---------------------------------------------------------------------------: | :------------------------------------------------------------------------------: |
|                    *Step 1: Input Account & Amount*                   |                       *Step 2: Chain Validators Trigger*                      |                       *Step 3: Instant Approval/Rejection*                       |

> *Place high-res screenshots in `docs/images/`.*

---

## 📜 License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

<p align="center">
  <em>Secure your banking app with robust validation.</em><br/>
  <a href="https://github.com/Tharindu714/Security-Validator-Banking-DialogBox"><strong>Get Started →</strong></a>
</p>
