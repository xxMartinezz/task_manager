package com.taskmanager.tm.services.user;

import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.repositories.specification.UserSpecification;
import com.taskmanager.tm.repositories.user.UserRepository;
import com.taskmanager.tm.services.dto.user.CreateUserDTO;
import com.taskmanager.tm.services.dto.user.PaginatedUserListDTO;
import com.taskmanager.tm.services.dto.user.UserFilterDTO;
import com.taskmanager.tm.services.dto.user.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDTO dto) {
        User user = UserConverter.toUser(dto);
        this.userRepository.save(user);
        log.debug("Created user: {}", user);
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
        return this.userRepository.findById(id).map(UserConverter::toUserResponse);
    }

    public void deleteUser(Long id) {
        this.userRepository.findById(id).ifPresent(user -> {
            this.userRepository.deleteById(id);
            log.debug("Deleted user: {}", user);
        });
    }
}
