package com.taskmanager.tm.services.dto.task;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.attachment.Attachment;
import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.entities.task.Task;
import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    private Sprint sprint;

    private Project project;

    @NotNull
    private User reportedUser;

    private User assignedUser;

    private List<Attachment> attachments;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.taskType = task.getTaskType();
        this.component = task.getComponent();
        this.description = task.getDescription();
        this.priority = task.getPriority();
        this.estimatedTime = task.getEstimatedTime();
        this.deadlineDate = task.getDeadlineDate();
        this.reportedDate = task.getReportedDate();
        this.lastChageDate = task.getLastChageDate();
        this.loggedTime = task.getLoggedTime();
        this.taskStatus = task.getTaskStatus();
        this.sprint = task.getSprint();
        this.project = task.getProject();
        this.reportedUser = task.getReportedUser();
        this.assignedUser = task.getAssignedUser();
        this.attachments = task.getAttachments();
    }
}
