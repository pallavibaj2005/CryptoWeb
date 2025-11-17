package com.crypto.service;

import com.crypto.model.Portfolio;
import com.crypto.model.Transaction;
import com.crypto.model.Crypto;
import com.crypto.repository.PortfolioRepository;
import com.crypto.repository.TransactionRepository;
import com.crypto.repository.CryptoRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;

@Service
public class BuyService {

    @Autowired
    private PortfolioRepository portfolioRepo;
    @Autowired
    private TransactionRepository txnRepo;
    @Autowired
    private CryptoRepository cryptoRepo;

    public String buy(String symbol, double quantity) {
        Crypto crypto = cryptoRepo.findById(symbol.toUpperCase())
                                  .orElse(new Crypto(symbol.toUpperCase(), symbol.toUpperCase(), 100)); // default price 100
        double price = crypto.getCurrentPrice();

        Portfolio portfolio = portfolioRepo.findById(symbol.toUpperCase()).orElse(new Portfolio(symbol.toUpperCase(), 0, 0));
        double newQty = portfolio.getQuantity() + quantity;
        double newAvg = ((portfolio.getQuantity() * portfolio.getAvgBuyPrice()) + (quantity * price)) / newQty;

        portfolio.setQuantity(newQty);
        portfolio.setAvgBuyPrice(newAvg);
        portfolioRepo.save(portfolio);

        Transaction txn = new Transaction("BUY", symbol.toUpperCase(), quantity, price, LocalDateTime.now());
        txnRepo.save(txn);

        return "Bought " + quantity + " " + symbol.toUpperCase() + " at $" + price;
    }
}
