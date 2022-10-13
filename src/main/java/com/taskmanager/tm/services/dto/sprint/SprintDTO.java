package com.taskmanager.tm.services.dto.sprint;

import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.entities.task.Task;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    private List<Task> tasks;

    public SprintDTO(Sprint sprint) {
        this.id = sprint.getId();
        this.name = sprint.getName();
        this.tasks = sprint.getTasks();
    }
}
