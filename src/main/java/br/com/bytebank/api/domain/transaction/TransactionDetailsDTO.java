package br.com.bytebank.api.domain.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDetailsDTO(
        Long transactionId,
        TransactionType type,
        BigDecimal amount,
        String description,
        LocalDateTime date,
        Long destinationAccountId,
        String destinationAccountNumber,
        BigDecimal accountBalanceAfterTransaction
) {
    public TransactionDetailsDTO (Transaction transaction) {
        this(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getDestinationAccount().getId(),
                transaction.getDestinationAccount().getAccountNumber(),
                transaction.getDestinationAccount().getBalance()  // Saldo já atualizado em conta
        );
    }
}
