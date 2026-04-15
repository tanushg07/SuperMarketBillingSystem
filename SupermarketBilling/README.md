# 🛒 FreshMart – Supermarket Billing System
### Java OOP Project with HTML/CSS/JS Frontend

---

## 📂 Project Structure

```
SupermarketBilling/
├── frontend/
│   └── index.html                  ← Full UI (open directly in browser)
│
└── src/main/java/com/supermarket/
    ├── Main.java                   ← Entry point, demonstrates all OOP
    ├── model/
    │   ├── Product.java            ← Abstract base class (Abstraction)
    │   ├── GroceryProduct.java     ← Inherits Product (Inheritance)
    │   ├── DairyProduct.java       ← Inherits Product (Inheritance)
    │   ├── ElectronicsProduct.java ← Inherits Product (Inheritance)
    │   ├── Customer.java           ← Encapsulation + MemberCustomer subclass
    │   ├── CartItem.java           ← Implements Taxable (Interface)
    │   ├── ShoppingCart.java       ← Manages CartItem collection
    │   ├── Bill.java               ← Full receipt generation
    │   └── PaymentMethod.java      ← Abstract + Cash/Card/UPI subclasses
    ├── service/
    │   ├── InventoryService.java   ← Singleton Pattern
    │   └── BillingService.java     ← Orchestrates billing flow
    ├── interfaces/
    │   ├── Taxable.java            ← Interface with GST constants
    │   └── Discountable.java       ← Interface for discount contracts
    └── exceptions/
        ├── ProductNotFoundException.java
        ├── OutOfStockException.java
        ├── EmptyCartException.java
        ├── PaymentFailedException.java
        └── BillNotFoundException.java
```

---

## 🎓 OOP Concepts Covered

| Concept | Where Used |
|---|---|
| **Encapsulation** | All model classes — private fields + getters/setters |
| **Inheritance** | GroceryProduct, DairyProduct, ElectronicsProduct → Product |
| | MemberCustomer → Customer |
| | CashPayment, CardPayment, UPIPayment → PaymentMethod |
| **Polymorphism** | `calculateDiscount()` overridden in each product type |
| | `processPayment()` overridden in each payment type |
| | `applyDiscount()` overridden in MemberCustomer |
| **Abstraction** | `abstract class Product` with `abstract calculateDiscount()` |
| | `abstract class PaymentMethod` with `abstract processPayment()` |
| **Interface** | `CartItem implements Taxable` |
| | `Customer implements Discountable` |
| **Singleton Pattern** | `InventoryService.getInstance()` |
| **Static Factory Method** | `PaymentMethod.of("UPI")` |
| **Exception Handling** | 5 custom checked exceptions |

---

## 🚀 How to Run

### Backend (Java)
```powershell
# Compile
$sources = Get-ChildItem -Path src/main/java -Recurse -Filter *.java | ForEach-Object FullName
javac -d out $sources

# Run
java -cp out com.supermarket.Main
```

### Frontend
Just open `frontend/index.html` in any modern browser — no server needed!

---

## ✨ Frontend Features

- 🛍️ **Product catalog** with search + category filter
- 🛒 **Live shopping cart** with quantity controls
- 👤 **Customer details** with membership toggle (5% member discount)
- 💳 **Payment methods** — UPI, Card, Cash
- 🧾 **Printable receipt** modal with full GST breakdown
- 📊 **Live stats bar** — total revenue, bills, cart count
- 📦 **Stock management** — low stock warnings, out-of-stock blocking
- 🎨 **OOP reference panel** — shows concepts used with class names

---

## 💡 Sample Output (Backend)

```
🛒 FRESH MART SUPERMARKET
=======================================================
Bill No    : BILL-1001
Customer   : Ravi Kumar | Member: false
-------------------------------------------------------
Basmati Rice (5kg)         x2   ₹   586.04  [Tax: ₹29.30]
Organic Honey (500g)       x1   ₹   332.50  [Tax: ₹16.63]
Full Cream Milk (1L)       x3   ₹   173.40  [Tax: ₹8.67]
-------------------------------------------------------
Subtotal:                               ₹ 1,091.94
Total Tax:                              ₹    54.60
=======================================================
GRAND TOTAL:                            ₹ 1,146.54
Payment Mode: UPI (customer@upi)
Loyalty Points Earned: 114 pts
=======================================================
```
