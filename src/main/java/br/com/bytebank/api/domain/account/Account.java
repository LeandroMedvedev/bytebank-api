package br.com.bytebank.api.domain.account;

import br.com.bytebank.api.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Table(name = "accounts")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    /*
     * LAZY informa ao JPA para só carregar os dados do
     * User associado quando explicitamente solicitados.
     *
     * Campo user é do tipo User (não Long) porque JPA é OOP,
     * então crio um link direto para o objeto User inteiro.
     */

    @Column(name = "agency_number")
    private String agencyNumber;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    private BigDecimal balance;  // BigDecimal ⇾ + precisão

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @Column(name = "is_active")
    private boolean isActive;

    // TODO: Adicionar campos createdAt e updatedAt com Spring Data Auditing
}
