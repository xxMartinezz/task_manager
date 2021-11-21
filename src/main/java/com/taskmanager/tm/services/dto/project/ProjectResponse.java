package com.taskmanager.tm.services.dto.project;

import com.taskmanager.tm.services.dto.task.TaskDTO;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ProjectResponse {
    Long id;
    String name;
    List<TaskDTO> tasks;
}
