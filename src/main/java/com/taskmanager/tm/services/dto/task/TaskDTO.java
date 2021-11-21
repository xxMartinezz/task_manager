package com.taskmanager.tm.services.dto.task;

import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;
import com.taskmanager.tm.services.dto.attachment.AttachmentDTO;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class TaskDTO {
    Long id;
    String name;
    TaskType taskType;
    String component;
    String description;
    Priority priority;
    String estimatedTime;
    LocalDateTime deadlineDate;
    LocalDateTime reportedDate;
    LocalDateTime lastChangeDate;
    String loggedTime;
    TaskStatus taskStatus;
    Long reportedUserId;
    Long assignedUserId;
    List<AttachmentDTO> attachments;
}
