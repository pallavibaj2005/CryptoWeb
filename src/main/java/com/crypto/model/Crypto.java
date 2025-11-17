package com.crypto.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Crypto {

    @Id
    private String symbol;
    private String name;
    private double currentPrice;

    public Crypto() {}
    public Crypto(String symbol, String name, double currentPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    // Getters & Setters
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
}
