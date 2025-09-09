package br.com.bytebank.api.controller;

import br.com.bytebank.api.domain.transaction.DepositRequestDTO;
import br.com.bytebank.api.domain.transaction.TransactionDetailsDTO;
import br.com.bytebank.api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<TransactionDetailsDTO> deposit(@RequestBody @Valid DepositRequestDTO requestDTO, UriComponentsBuilder uriBuilder) {
        var newTransactionDetails = transactionService.performDeposit(requestDTO);
        var uri = uriBuilder.path("/transactions/{id}").buildAndExpand(newTransactionDetails.transactionId()).toUri();

        return ResponseEntity.created(uri).body(newTransactionDetails);
    }
}
