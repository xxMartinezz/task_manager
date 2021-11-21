package com.taskmanager.tm.controllers.user;

import com.taskmanager.tm.services.dto.user.PaginatedUserListDTO;
import com.taskmanager.tm.services.dto.user.UserDTO;
import com.taskmanager.tm.services.dto.user.UserFilterDTO;
import com.taskmanager.tm.services.dto.user.UserResponse;
import com.taskmanager.tm.services.exceptions.RequestException;
import com.taskmanager.tm.services.user.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("Post method, create user: {}", userDTO);
        userService.createUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug("Put method, update user: {}", userDTO);
        userService.updateUser(userDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PaginatedUserListDTO> getUsers(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "surname") String sortBy,
            @ModelAttribute UserFilterDTO userFilter) {
        PaginatedUserListDTO paginatedUserList = userService.getUsers(pageNumber, pageSize, sortBy, userFilter);
        return new ResponseEntity<PaginatedUserListDTO>(paginatedUserList, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable(name = "id") Long id) {
        Optional<UserResponse> user = userService.getUserById(id);
        if (!user.isPresent()) {
            throw new RequestException("There is no user with id " + id, "User");
        }
        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/fetch")
    public ResponseEntity<List<UserResponse>> fetchUsersByName(@RequestParam String nameOrSurname) {
        return new ResponseEntity<>(userService.fetchUsersByNameOrSurname(nameOrSurname), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Delete method, deleted user id: " + id);
    }
}
