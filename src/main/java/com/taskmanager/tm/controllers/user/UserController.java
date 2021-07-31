package com.taskmanager.tm.controllers.user;

import com.taskmanager.tm.repositories.user.UserRepository;
import com.taskmanager.tm.services.dto.user.CreateUserDTO;
import com.taskmanager.tm.services.dto.user.UserResponse;
import com.taskmanager.tm.services.exceptions.RequestException;
import com.taskmanager.tm.services.user.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserDTO userDTO) {
        log.debug("Post method, create user: {}", userDTO);
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserResponse>> getAllUsers(Pageable pageable) {
        Page<UserResponse> page = this.userService.getAllUsers(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
        Optional<UserResponse> user = this.userService.getUserById(id);
        if (!user.isPresent()) {
            throw new RequestException("There is no user with ID " + id + " ", "User");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Delete method, deleted user id: " + id);
    }
}
