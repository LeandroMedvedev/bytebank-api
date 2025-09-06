package br.com.bytebank.api.controller;

import br.com.bytebank.api.domain.user.UserCreationDTO;
import br.com.bytebank.api.domain.user.UserDetailsDTO;
import br.com.bytebank.api.domain.user.UserUpdateDTO;
import br.com.bytebank.api.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> listAllUsers() {
        var users = userService.getAllUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> registerUser(
            @RequestBody UserCreationDTO creationDTO, UriComponentsBuilder uriBuilder
    ) {
        var newUserDetails = userService.createUser(creationDTO);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(newUserDetails.id()).toUri();

        return ResponseEntity.created(uri).body(newUserDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUserDetails(@PathVariable Long id) {
            var userDetails = userService.getUserById(id);

            return ResponseEntity.ok(userDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> updateUser(
            @PathVariable Long id, @RequestBody UserUpdateDTO updateDTO
    ) {
            var updatedUser = userService.updateUser(id, updateDTO);
            return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
    }
}
