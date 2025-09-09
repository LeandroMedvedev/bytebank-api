package br.com.bytebank.api.service;

import br.com.bytebank.api.domain.transaction.DepositRequestDTO;
import br.com.bytebank.api.domain.transaction.Transaction;
import br.com.bytebank.api.domain.transaction.TransactionDetailsDTO;
import br.com.bytebank.api.domain.transaction.TransactionType;
import br.com.bytebank.api.exception.BusinessRuleException;
import br.com.bytebank.api.exception.ResourceNotFoundException;
import br.com.bytebank.api.repository.AccountRepository;
import br.com.bytebank.api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    /*
     * @Transactional garante que:
     * OU tudo funciona -> saldo é atualizado + operação é registrada.
     * OU nada é salvo (tudo é revertido) mantendo dados consistentes.
     */
    @Transactional
    public TransactionDetailsDTO performDeposit (DepositRequestDTO requestDTO) {
        var destinationAccount = accountRepository.findByAccountNumber(requestDTO.destinationAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Destination account not found."));

        if (!destinationAccount.isActive()) {
            throw new BusinessRuleException("Cannot deposit into an inactive account.");
        }

        destinationAccount.setBalance(destinationAccount.getBalance().add(requestDTO.amount()));
        // Usei .add() para somar, porque BigDecimal é imutável

        var transaction = new Transaction(
                null,
                requestDTO.amount(),
                TransactionType.DEPOSIT,
                LocalDateTime.now(),
                null,  // Conta de origem é nula para depósitos
                destinationAccount,
                "Deposit operation"
        );

        transactionRepository.save(transaction);

        return new TransactionDetailsDTO(transaction);
    }
}
