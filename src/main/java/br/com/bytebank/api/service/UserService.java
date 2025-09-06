package br.com.bytebank.api.service;

import br.com.bytebank.api.domain.user.User;
import br.com.bytebank.api.domain.user.UserCreationDTO;
import br.com.bytebank.api.domain.user.UserDetailsDTO;
import br.com.bytebank.api.domain.user.UserUpdateDTO;
import br.com.bytebank.api.exception.ResourceNotFoundException;
import br.com.bytebank.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDetailsDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserDetailsDTO::new)
                .toList();
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

    public UserDetailsDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        return new UserDetailsDTO(user);
    }

    @Transactional
    public UserDetailsDTO updateUser(Long id, UserUpdateDTO updateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (updateDTO.name() != null) {
            user.setName(updateDTO.name());
        }

        if (updateDTO.email() != null) {
            user.setEmail(updateDTO.email());
        }

        /*
         * @Transactional já garante que a alteração será salva ao final do método.
         * JPA detecta a alteração no objeto 'user' e persiste no banco.
         * Daí não haver necessidade de chamar userRepository.save(user) aqui.
         */

        return new UserDetailsDTO(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id " + id);
        }

        /*
         * @Transactional garante que a exclusão será gerenciada como transação segura no db.
         */

        userRepository.deleteById(id);
    }
}
