package com.taskmanager.tm.services.dto.task;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedTaskListDTO {
    private List<TaskDTO> data;
    private Long count;
    private int pages;
}
