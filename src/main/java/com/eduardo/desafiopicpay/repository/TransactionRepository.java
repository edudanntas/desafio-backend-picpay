package com.eduardo.desafiopicpay.repository;

import com.eduardo.desafiopicpay.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
