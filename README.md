# 🚀 CryptoWeb Backend – Advanced Spring Boot Crypto Trading API (CoinGecko Integrated)

CryptoWeb is a powerful and modular Spring Boot backend that simulates a complete cryptocurrency trading ecosystem. Leveraging real-time data from CoinGecko, it offers robust portfolio management, accurate transaction histories, and rich RESTful APIs—making it a perfect foundation for crypto web apps, learning projects, or fintech experiments.

---

## 🌟 Feature Highlights

### 📈 Real-Time Market Data
- **Top 10 Cryptos:** Instantly fetch the top 10 coins by market cap.
- **Smart Coin Resolution:** Automatically map common symbols/names to exact CoinGecko IDs (250+ crypto mappings preloaded).
- **Live Price Feeds:** Accurate buy/sell executions based on real market prices.

### 💱 Trading Engine
- **Buy/Sell Logic:** Securely buy & sell cryptocurrencies. All trades update the portfolio and recalculate weighted averages (cost basis).
- **P/L Calculation:** Integrated profit/loss logic as part of every sale.
- **Persistent Transactions:** Complete record of every trade—backed by a transactional database.
- **Portfolio Consistency:** Never lose track of holdings, average prices, or history.

### 💼 User Portfolio & Transaction Tracking
- **Portfolio View:** Instantly view your complete crypto holdings with up-to-date market values.
- **Transaction History:** Audit all buys/sells with detailed logs.
- **Database-Driven:** Powered by JPA (Hibernate) for real persistence—compatible with H2 for dev, or MySQL for production.

---

## 🧱 Clean Project Architecture

```
src/main/java/com/crypto/
│
├── controller/
│   └── TraderController.java              # Main API logic
│
├── model/
│   ├── Crypto.java                        # Market data model
│   ├── Portfolio.java                     # User portfolio entity
│   └── Transaction.java                   # Trade log entity
│
├── repository/
│   ├── CryptoRepository.java              # Market data persistence
│   ├── PortfolioRepository.java           # Portfolio storage
│   └── TransactionRepository.java         # Transaction log
│
├── service/
│   ├── BuyService.java                    # Buy logic
│   ├── SellService.java                   # Sell logic
│   ├── PortfolioService.java              # Portfolio handling
│   └── TransactionService.java            # Transaction operations
```

---


## 🧠 Core Business Logic

### 🟢 Buy Crypto
- Map symbol → real CoinGecko ID
- Fetch real-time price
- Update portfolio: increase quantity & recalculate average buy price
- Store **BUY** transaction in DB

### 🔴 Sell Crypto
- Validate sufficient holdings
- Fetch real-time price
- Calculate profit/loss (P/L), update average buy price
- Update portfolio: decrease quantity
- Store **SELL** transaction in DB

---

---

## ⚙️ Tech Stack

- **Java 17+**
- **Spring Boot** (RESTful microservices)
- **JPA + Hibernate** (database)
- **CoinGecko API** (Public crypto pricing)
- **RestTemplate** (API calls)
- **MySQL** 

---


```bash
mvn spring-boot:run
```
Server runs at: [http://localhost:8080](http://localhost:8080)

---

## 🎯 Why CryptoWeb?

- **Production-grade trading logic**
- **Seamless integration of live market data**
- **Robust JPA modeling for persistence & analytics**
- **Clean, modular Java architecture**
- **Perfect for learning, prototyping, teaching fintech, or powering client apps**

---

## 💬 Feedback / Contribution

Your contributions, issues, and ideas are welcome!  
Feel free to fork, star, or submit pull requests.

---
