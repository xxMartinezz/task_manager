package com.taskmanager.tm.services.dto.user;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedUserListDTO {
    private List<UserResponse> data;
    private Long count;
    private int pages;
}
