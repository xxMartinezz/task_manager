package com.taskmanager.tm.services.user;

import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.services.dto.user.UserDTO;
import com.taskmanager.tm.services.dto.user.UserResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class UserConverter {

    static User toUser(UserDTO dto) {
        return User.builder()
                .withName(dto.getName())
                .withSurname(dto.getSurname())
                .withActive(dto.isActive())
                .build();
    }

    static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .active(user.isActive())
                .build();
    }

    static List<UserResponse> toUserResponseList(List<User> users) {
        return users.stream()
                .filter(Objects::nonNull)
                .map(user -> toUserResponse(user))
                .collect(Collectors.toList());
    }
}
