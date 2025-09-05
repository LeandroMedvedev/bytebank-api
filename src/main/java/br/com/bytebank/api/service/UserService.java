package br.com.bytebank.api.service;

import br.com.bytebank.api.domain.user.User;
import br.com.bytebank.api.domain.user.UserCreationDTO;
import br.com.bytebank.api.domain.user.UserDetailsDTO;
import br.com.bytebank.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsDTO createUser(UserCreationDTO creationDTO) {
        /*
         * TODO:
         *  Adicionar validação para impossibilitar e-mail ou documento duplicado
         *  Criptografar senha
         */

        var user = new User(
                null,
                creationDTO.name(),
                creationDTO.email(),
                creationDTO.password(),
                creationDTO.documentNumber()
        );

        var savedUser = userRepository.save(user);

        return new UserDetailsDTO(savedUser);
    }
}
