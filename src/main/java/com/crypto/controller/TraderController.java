package com.crypto.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/trader")
public class TraderController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, Double> portfolio = new HashMap<>();
    private final List<String> transactions = new ArrayList<>();
    private final Map<String, String> symbolToId = new HashMap<>();
    private final Map<String, String> idToSymbol = new HashMap<>();

    public TraderController() {
        // Preload known crypto mappings (commonly used)
        preloadCoinMappings();
    }

    private void preloadCoinMappings() {
        try {
            String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1";
            List<Map<String, Object>> coins = restTemplate.getForObject(url, List.class);
            if (coins != null) {
                for (Map<String, Object> coin : coins) {
                    String id = (String) coin.get("id");
                    String symbol = ((String) coin.get("symbol")).toLowerCase();
                    symbolToId.put(symbol, id);
                    idToSymbol.put(id, symbol.toUpperCase());
                }
            }
            System.out.println("✅ Coin mappings loaded: " + symbolToId.size());
        } catch (Exception e) {
            System.err.println("⚠️ Could not load coin mappings: " + e.getMessage());
        }
    }

    // ✅ Utility: resolve any user input (name or symbol)
    private String resolveCoinId(String userInput) {
        String input = userInput.trim().toLowerCase();
        if (symbolToId.containsKey(input)) return symbolToId.get(input);
        // Try direct lookup from CoinGecko
        try {
            String url = "https://api.coingecko.com/api/v3/coins/" + input;
            Map<?, ?> coin = restTemplate.getForObject(url, Map.class);
            if (coin != null && coin.containsKey("id")) {
                String id = (String) coin.get("id");
                String symbol = ((Map<String, Object>) coin.get("symbol")).toString().toLowerCase();
                symbolToId.put(symbol, id);
                idToSymbol.put(id, symbol.toUpperCase());
                return id;
            }
        } catch (Exception ignored) {}
        return null;
    }

    // ✅ Test
    @GetMapping("/test")
    public ResponseEntity<Map<String, String>> testBackend() {
        Map<String, String> res = new HashMap<>();
        res.put("message", "Backend is working fine!");
        return ResponseEntity.ok(res);
    }

    // ✅ BUY
    @PostMapping("/buy/{coinInput}/{amount}")
    public ResponseEntity<Map<String, String>> buy(@PathVariable String coinInput, @PathVariable double amount) {
        String coinId = resolveCoinId(coinInput);
        Map<String, String> res = new HashMap<>();

        if (coinId == null) {
            res.put("message", "❌ Invalid coin name/symbol: " + coinInput);
            return ResponseEntity.badRequest().body(res);
        }

        String symbol = idToSymbol.get(coinId);
        portfolio.put(symbol, portfolio.getOrDefault(symbol, 0.0) + amount);
        transactions.add("Bought " + amount + " of " + symbol + " at " + new Date());
        res.put("message", "✅ Bought " + amount + " of " + symbol);
        return ResponseEntity.ok(res);
    }

    // ✅ SELL
    @PostMapping("/sell/{coinInput}/{amount}")
    public ResponseEntity<Map<String, String>> sell(@PathVariable String coinInput, @PathVariable double amount) {
        String coinId = resolveCoinId(coinInput);
        Map<String, String> res = new HashMap<>();

        if (coinId == null) {
            res.put("message", "❌ Invalid coin name/symbol: " + coinInput);
            return ResponseEntity.badRequest().body(res);
        }

        String symbol = idToSymbol.get(coinId);
        double balance = portfolio.getOrDefault(symbol, 0.0);
        if (balance < amount) {
            res.put("message", "❌ Not enough " + symbol + " to sell.");
            return ResponseEntity.badRequest().body(res);
        }

        portfolio.put(symbol, balance - amount);
        transactions.add("Sold " + amount + " of " + symbol + " at " + new Date());
        res.put("message", "✅ Sold " + amount + " of " + symbol);
        return ResponseEntity.ok(res);
    }

    // ✅ Portfolio
    @GetMapping("/portfolio")
    public ResponseEntity<Map<String, Double>> getPortfolio() {
        return ResponseEntity.ok(portfolio);
    }

    // ✅ Transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<String>> getTransactions() {
        return ResponseEntity.ok(transactions);
    }

    // ✅ Top 10
    @GetMapping("/top10")
    public ResponseEntity<?> getTop10() {
        try {
            String url = "https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=10&page=1&sparkline=false";
            Object response = restTemplate.getForObject(url, Object.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error fetching top 10: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to fetch data"));
        }
    }

    // ✅ History (fixed parsing)
    @GetMapping("/history/{coinInput}")
    public ResponseEntity<?> getHistory(@PathVariable String coinInput) {
        String coinId = resolveCoinId(coinInput);
        if (coinId == null)
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid coin name/symbol: " + coinInput));
        try {
            String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=90";
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("Error fetching history: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unable to fetch history"));
        }
    }
}
