package com.example.test.service;

import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    public void process(String message) {
        System.out.println("processing ... " + message);
    }
}
