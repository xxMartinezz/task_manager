package com.taskmanager.tm.services.dto.sprint;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.task.Task;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SprintDTO {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    private Task task;
}
