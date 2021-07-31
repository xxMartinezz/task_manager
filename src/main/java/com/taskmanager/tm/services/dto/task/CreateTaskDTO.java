package com.taskmanager.tm.services.dto.task;

import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskType;
import com.taskmanager.tm.services.dto.attachment.AttachmentDTO;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateTaskDTO {

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    private TaskType taskType;

    private String component;

    @NotNull
    @Size(min = 1, max = 500)
    private String description;

    @NotNull
    private Priority priority;

    private String estimatedTime;

    private LocalDateTime deadlineDate;

    private Long sprintId;

    @NotNull
    private Long projectId;

    @NotNull
    private Long reportedUserId;

    private Long assignedUserId;

    private List<AttachmentDTO> attachments = new ArrayList<>();
}
