package com.crypto.service;

import com.crypto.model.Transaction;
import com.crypto.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository txnRepo;

    public List<Transaction> getAllTransactions() {
        return txnRepo.findAll();
    }
}
