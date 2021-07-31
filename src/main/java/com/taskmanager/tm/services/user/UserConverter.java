package com.taskmanager.tm.services.user;

import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.services.dto.user.CreateUserDTO;
import com.taskmanager.tm.services.dto.user.UserResponse;

class UserConverter {

    static User toUser(CreateUserDTO dto) {
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
}
