package br.com.bytebank.api.domain.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record WithdrawalRequestDTO(
        @NotBlank(message = "Source account number is mandatory")
        String sourceAccountNumber,

        @NotNull(message = "Amount is mandatory")
        @Positive(message = "Amount must be positive")
        BigDecimal amount
) {
}
