package com.taskmanager.tm.services.dto.attachment;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.task.Task;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentDTO {

    @NotNull
    private Long id;

    private String name;

    private String url;

    private String mimeType;

    private long size;

    private Task task;
}
