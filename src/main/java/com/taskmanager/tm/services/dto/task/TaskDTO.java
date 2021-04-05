package com.taskmanager.tm.services.dto.task;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.attachment.Attachment;
import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDTO {

    @NotNull
    private Long id;

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

    private LocalDateTime reportedDate;

    private LocalDateTime lastChageDate;

    private String loggedTime;

    @NotNull
    private TaskStatus taskStatus;

    private List<Sprint> sprints;

    private Project project;

    @NotNull
    private User reportedUser;

    private User assignedUser;

    private List<Attachment> attachments;
}
