package com.crypto.service;

import com.crypto.model.Portfolio;
import com.crypto.repository.PortfolioRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class PortfolioService {
    @Autowired
    private PortfolioRepository portfolioRepo;

    public List<Portfolio> getPortfolio() {
        return portfolioRepo.findAll();
    }
}
