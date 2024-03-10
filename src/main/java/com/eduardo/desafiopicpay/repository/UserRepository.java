package com.eduardo.desafiopicpay.repository;

import com.eduardo.desafiopicpay.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
