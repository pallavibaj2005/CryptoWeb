🚀 CryptoWeb Backend – Spring Boot Crypto Trading API (CoinGecko Integrated)

CryptoWeb is a Spring Boot–based cryptocurrency trading backend engineered to simulate a full trading ecosystem.
It integrates live market data from CoinGecko, manages a persistent portfolio, records transactions, and exposes REST APIs for buy/sell, market trends, and historical data.

The system follows a clean Model–Repository–Service–Controller architecture, supporting both real-time API calls and database-backed portfolio management.

🔥 Key Features
✅ Live Crypto Data (CoinGecko API)

Fetch top 10 coins by market cap

Fetch 90-day historical price chart

Auto-resolve coin name or symbol to CoinGecko ID

Preloads 250+ crypto mappings at startup

✅ Trading Engine

Buy coins (updates average price + quantity)

Sell coins (calculates P/L)

Stores every trade as a database transaction

Maintains persistent user portfolio

✅ Portfolio & Transactions

View stored portfolio (JPA)

View full transaction history

Fully database-driven (Portfolio + Transaction tables)

✅ Utility Endpoints

Test API /api/trader/test

Auto-symbol resolution system

Error handling for invalid coins

🧱 Project Architecture
src/main/java/com/crypto/
│
├── controller/
│   └── TraderController.java
│
├── model/
│   ├── Crypto.java
│   ├── Portfolio.java
│   └── Transaction.java
│
├── repository/
│   ├── CryptoRepository.java
│   ├── PortfolioRepository.java
│   └── TransactionRepository.java
│
├── service/
│   ├── BuyService.java
│   ├── SellService.java
│   ├── PortfolioService.java
│   └── TransactionService.java

📡 REST API Endpoints
📍 Trading API (Live API + Calculations)

Base URL: /api/trader

Test Backend
GET /api/trader/test

Buy Crypto
POST /api/trader/buy/{symbol}/{amount}

Sell Crypto
POST /api/trader/sell/{symbol}/{amount}

Get User Portfolio (in-memory)
GET /api/trader/portfolio

Get Transactions (in-memory)
GET /api/trader/transactions

Top 10 Cryptos (Live Market Data)
GET /api/trader/top10

90-Day Price History
GET /api/trader/history/{symbol}

🧠 Business Logic Overview
🔹 Buy Flow

Resolve coin ID → fetch real price

Update Portfolio:

Increase quantity

Recalculate average buy price

Store Transaction (BUY)

🔹 Sell Flow

Validate holdings

Fetch real price

Calculate P/L

Update Portfolio

Store Transaction (SELL)

🗄 Database Entities
Crypto

symbol (PK)

name

currentPrice

Portfolio

symbol (PK)

quantity

avgBuyPrice

Transaction

id (PK)

type (BUY/SELL)

symbol

quantity

price

dateTime

⚙️ Technologies Used

Java 17+

Spring Boot

JPA + Hibernate

CoinGecko Public APIs

RestTemplate

H2 / MySQL (Based on configuration)

▶️ Running the Project
mvn spring-boot:run


Backend runs at:

http://localhost:8080

🎯 Purpose of This Project

This backend showcases:

Real-world crypto data integration

Full trading logic (buy/sell/portfolio management)

Clean modular architecture

REST API development best practices

JPA entity modeling & persistence

Finance-oriented backend logic

End-to-end trading simulation
