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
public class SellService {

    @Autowired
    private PortfolioRepository portfolioRepo;
    @Autowired
    private TransactionRepository txnRepo;
    @Autowired
    private CryptoRepository cryptoRepo;

    public String sell(String symbol, double quantity) {
        Portfolio portfolio = portfolioRepo.findById(symbol.toUpperCase())
                                           .orElseThrow(() -> new RuntimeException("No holdings for " + symbol));
        if (quantity > portfolio.getQuantity()) {
            return "Not enough quantity to sell!";
        }

        Crypto crypto = cryptoRepo.findById(symbol.toUpperCase())
                                  .orElse(new Crypto(symbol.toUpperCase(), symbol.toUpperCase(), 100));
        double price = crypto.getCurrentPrice();

        double pl = (price - portfolio.getAvgBuyPrice()) * quantity;

        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        portfolioRepo.save(portfolio);

        Transaction txn = new Transaction("SELL", symbol.toUpperCase(), quantity, price, LocalDateTime.now());
        txnRepo.save(txn);

        return "Sold " + quantity + " " + symbol.toUpperCase() + " | P/L: $" + String.format("%.2f", pl);
    }
}
