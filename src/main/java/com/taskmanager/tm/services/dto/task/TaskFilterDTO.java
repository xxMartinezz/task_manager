package com.taskmanager.tm.services.dto.task;

import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskFilterDTO {
    private String name;
    private TaskType taskType;
    private String component;
    private String description;
    private Priority priority;
    private LocalDateTime deadlineDateFrom;
    private LocalDateTime deadlineDateTo;
    private LocalDateTime reportedDateFrom;
    private LocalDateTime reportedDateTo;
    private LocalDateTime lastChangeDate;
    private String estimatedTime;
    private String loggedTime;
    private TaskStatus taskStatus;
    private Long reportedUserId;
    private Long assignedUserId;
}
