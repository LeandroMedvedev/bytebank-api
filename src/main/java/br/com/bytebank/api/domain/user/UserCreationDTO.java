package br.com.bytebank.api.domain.user;

public record UserCreationDTO(
        String name,
        String email,
        String password,
        String documentNumber
) {
}
