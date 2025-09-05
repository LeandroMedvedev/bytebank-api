package br.com.bytebank.api.repository;

import br.com.bytebank.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
