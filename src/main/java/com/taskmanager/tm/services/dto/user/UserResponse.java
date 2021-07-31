package com.taskmanager.tm.services.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserResponse {
    Long id;
    String name;
    String surname;
    boolean active;
}
