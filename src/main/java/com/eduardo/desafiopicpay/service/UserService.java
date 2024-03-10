package com.eduardo.desafiopicpay.service;

import com.eduardo.desafiopicpay.domain.entities.User;
import com.eduardo.desafiopicpay.domain.enums.UserType;
import com.eduardo.desafiopicpay.dto.UserDTO;
import com.eduardo.desafiopicpay.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(BigDecimal value, User sender) throws Exception {
        if (sender.getUserType() == UserType.MERCHANT){
            throw new Exception("User with MERCHANT type can't send money");
        }
        if (sender.getBalance().compareTo(value) < 0){
            throw new Exception("User don't have money enough to make a transaction");
        }
    }

    public User findById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("User can't be found in database"));
    }

    public User createUser(UserDTO userDTO){
        User user = new User(userDTO);
        return repository.save(user);
    }

    public List<User> findAll(){
        return repository.findAll();
    }

    public void saveUser(User user){
        repository.save(user);
    }
}
