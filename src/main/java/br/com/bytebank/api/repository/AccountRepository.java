package br.com.bytebank.api.repository;

import br.com.bytebank.api.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {}
