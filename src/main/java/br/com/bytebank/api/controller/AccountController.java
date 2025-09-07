package br.com.bytebank.api.controller;

import br.com.bytebank.api.domain.account.AccountCreationDTO;
import br.com.bytebank.api.domain.account.AccountDetailsDTO;
import br.com.bytebank.api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountDetailsDTO> createAccount(
            @RequestBody @Valid AccountCreationDTO creationDTO,
            UriComponentsBuilder uriBuilder
            ) {
        var newAccountDetails = accountService.createAccount(creationDTO);
        var uri = uriBuilder.path("/accounts/{id}").buildAndExpand(newAccountDetails.id()).toUri();

        return ResponseEntity.created(uri).body(newAccountDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDetailsDTO> getAccountDetails(@PathVariable Long id) {
        var accountDetails = accountService.getAccountById(id);

        return ResponseEntity.ok(accountDetails);
    }
}
