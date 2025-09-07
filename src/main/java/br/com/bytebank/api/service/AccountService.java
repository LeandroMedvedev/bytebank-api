package br.com.bytebank.api.service;

import br.com.bytebank.api.domain.account.Account;
import br.com.bytebank.api.domain.account.AccountCreationDTO;
import br.com.bytebank.api.domain.account.AccountDetailsDTO;
import br.com.bytebank.api.exception.ResourceNotFoundException;
import br.com.bytebank.api.repository.AccountRepository;
import br.com.bytebank.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class AccountService {

    private static final String DEFAULT_AGENCY = "0001";
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(
            AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AccountDetailsDTO createAccount(AccountCreationDTO creationDTO) {
        // Usuário dono da conta existe?
        var user = userRepository.findById(creationDTO.userId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + creationDTO.userId()));

        var accountNumber = generateUniqueAccountNumber();
        var initialBalance = BigDecimal.ZERO;
        var isActive = true;

        var newAccount = new Account(
                null,
                user,
                DEFAULT_AGENCY,
                accountNumber,
                initialBalance,
                creationDTO.accountType(),
                isActive
        );

        var savedAccount = accountRepository.save(newAccount);

        return new AccountDetailsDTO(savedAccount);
    }

    public AccountDetailsDTO getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));

        return new AccountDetailsDTO(account);
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {  // Loop para garantir que o número gerado seja único
            int number = 100000 + new Random().nextInt(900000);
            accountNumber = String.valueOf(number);
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());

        return accountNumber;
    }
}
