package com.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CryptoTraderBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoTraderBackendApplication.class, args);
        System.out.println("CryptoWeb Backend Running Successfully!");
    }
}
