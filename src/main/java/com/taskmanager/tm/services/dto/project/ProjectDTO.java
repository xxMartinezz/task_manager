package com.taskmanager.tm.services.dto.project;

import com.sun.istack.NotNull;
import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.entities.task.Task;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    private String name;

    private List<Task> tasks;

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.tasks = project.getTasks();
    }
}
