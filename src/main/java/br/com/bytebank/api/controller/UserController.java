package br.com.bytebank.api.controller;

import br.com.bytebank.api.domain.user.UserCreationDTO;
import br.com.bytebank.api.domain.user.UserDetailsDTO;
import br.com.bytebank.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController()
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> registerUser(@RequestBody UserCreationDTO creationDTO, UriComponentsBuilder uriBuilder) {
        var newUserDetails = userService.createUser(creationDTO);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(newUserDetails.id()).toUri();

        return ResponseEntity.created(uri).body(newUserDetails);
    }
}
