package com.taskmanager.tm.controllers.user;

import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.repositories.user.UserRepository;
import com.taskmanager.tm.services.dto.user.UserDTO;
import com.taskmanager.tm.services.exceptions.RequestException;
import com.taskmanager.tm.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("Post method, create user: {}", userDTO);
        if (userDTO.getId() != null) {
            throw new RequestException("The new user cannot have an ID", "User");
        } else {
            User newUser = this.userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("New user: " + newUser.toString());
        }
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        Page<UserDTO> page = this.userService.getAllUsers(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") Long id) {
        Optional<UserDTO> user = this.userService.getUserById(id);
        if (!user.isPresent()) {
            throw new RequestException("There is no user with ID " + id + " ", "User");
        }
        return new ResponseEntity<UserDTO>(user.get(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Delete method, deleted user id: " + id);
    }
}
