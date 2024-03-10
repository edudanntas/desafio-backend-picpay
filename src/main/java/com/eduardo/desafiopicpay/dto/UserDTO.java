package com.eduardo.desafiopicpay.dto;

import com.eduardo.desafiopicpay.domain.enums.UserType;

import java.math.BigDecimal;

public record UserDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String document,
        BigDecimal balance,
        UserType userType
) {
}
