package com.eduardo.desafiopicpay.service;

import com.eduardo.desafiopicpay.domain.entities.Transaction;
import com.eduardo.desafiopicpay.domain.entities.User;
import com.eduardo.desafiopicpay.dto.TransactionDTO;
import com.eduardo.desafiopicpay.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private RestTemplate restTemplate;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = userService.findById(transaction.senderId());
        User receiver = userService.findById(transaction.receiverId());

        userService.validateTransaction(transaction.value(), sender);

        boolean isAuthorized = authorizeTransaction(transaction.value(), sender);
        if (!isAuthorized){
            throw new Exception("Invalid transaction");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setReceiver(receiver);
        newTransaction.setSender(sender);
        newTransaction.setTransactionDate(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));
        repository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(receiver);

        notificationService.createNotification(sender, "Transaction send with success");
        notificationService.createNotification(receiver, "Transaction received with success");

        return newTransaction;
    }

    private boolean authorizeTransaction(BigDecimal value, User sender){
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK){
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
