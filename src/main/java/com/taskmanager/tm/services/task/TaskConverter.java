package com.taskmanager.tm.services.task;

import com.taskmanager.tm.entities.attachment.Attachment;
import com.taskmanager.tm.entities.task.Task;
import com.taskmanager.tm.services.dto.attachment.AttachmentDTO;
import com.taskmanager.tm.services.dto.task.CreateTaskDTO;
import com.taskmanager.tm.services.dto.task.TaskResponse;

import java.util.List;

public class TaskConverter {
    static Task toTask(CreateTaskDTO dto) {
        return Task.builder()
                .withName(dto.getName())
                .withTaskType(dto.getTaskType())
                .withComponent(dto.getComponent())
                .withDescription(dto.getDescription())
                .withPriority(dto.getPriority())
                .withEstimatedTime(dto.getEstimatedTime())
                .withDeadlineDate(dto.getDeadlineDate())
                .build();
    }

    static Attachment toAttachment(AttachmentDTO dto) {
        return Attachment.builder()
                .withName(dto.getName())
                .withUrl(dto.getUrl())
                .withMimeType(dto.getMimeType())
                .withSize(dto.getSize())
                .build();
    }

    public static TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .taskType(task.getTaskType())
                .component(task.getComponent())
                .description(task.getDescription())
                .priority(task.getPriority())
                .estimatedTime(task.getEstimatedTime())
                .deadlineDate(task.getDeadlineDate())
                .reportedDate(task.getReportedDate())
                .lastChangeDate(task.getLastChangeDate())
                .loggedTime(task.getLoggedTime())
                .taskStatus(task.getTaskStatus())
                .reportedUserId(task.getReportedUser() != null ? task.getReportedUser().getId() : null)
                .assignedUserId(task.getAssignedUser() != null ? task.getAssignedUser().getId() : null)
                .attachments(task.getAttachments().stream().map(TaskConverter::toAttachmentDTO).toList())
                .build();
    }

    public static List<TaskResponse> toTaskResponses(List<Task> taskList) {
        List<TaskResponse> taskResponseList = taskList.stream().map(TaskConverter::toTaskResponse).toList();
        return taskResponseList;
    }

    private static AttachmentDTO toAttachmentDTO(Attachment attachment) {
        return new AttachmentDTO(attachment.getName(), attachment.getUrl(),
                attachment.getMimeType(), attachment.getSize());
    }
}
