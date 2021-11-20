package com.taskmanager.tm.services.user;

import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.repositories.specification.UserSpecification;
import com.taskmanager.tm.repositories.user.UserRepository;
import com.taskmanager.tm.services.dto.user.UserDTO;
import com.taskmanager.tm.services.dto.user.PaginatedUserListDTO;
import com.taskmanager.tm.services.dto.user.UserFilterDTO;
import com.taskmanager.tm.services.dto.user.UserResponse;
import com.taskmanager.tm.services.exceptions.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserDTO dto) {
        User user = UserConverter.toUser(dto);
        userRepository.save(user);
        log.debug("Created user: {}", user);
    }

    public void updateUser(UserDTO userDTO) throws RequestException {
        validateUserId(userDTO);
        updateAndSaveUser(userDTO);
    }

    private void validateUserId(UserDTO userDTO) throws RequestException {
        if (userDTO.getId() == null) {
            throw new RequestException("There is no id in sent json", "User");
        }
    }

    private void updateAndSaveUser(UserDTO userDTO) {
        User user = userRepository.getById(userDTO.getId());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setActive(userDTO.isActive());
        userRepository.save(user);
    }

    public PaginatedUserListDTO getUsers(Integer pageNumber, Integer pageSize, String sortBy, UserFilterDTO userFilter) {
        log.debug("Getting filtered users");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        UserSpecification userSpecification = UserSpecification.builder()
                .filter(userFilter)
                .build();
        Page<User> pageResult = userRepository.findAll(userSpecification, pageable);
        return PaginatedUserListDTO.builder()
                .data(UserConverter.toUserResponseList(pageResult.getContent()))
                .count(pageResult.getTotalElements())
                .pages(pageResult.getTotalPages())
                .build();
    }

    public Optional<UserResponse> getUserById(Long id) {
        return userRepository.findById(id).map(UserConverter::toUserResponse);
    }

    public List<UserResponse> fetchUsersByNameOrSurname(String nameOrSurname) {
        log.debug("Getting users by name or surname {}", nameOrSurname);
        List<User> users = userRepository.findUsersByName(nameOrSurname);
        return UserConverter.toUserResponseList(users);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.deleteById(id);
            log.debug("Deleted user: {}", user);
        });
    }

}
