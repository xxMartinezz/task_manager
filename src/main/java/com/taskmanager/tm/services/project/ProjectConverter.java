package com.taskmanager.tm.services.project;

import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.services.dto.project.CreateProjectDTO;
import com.taskmanager.tm.services.dto.project.ProjectResponse;
import com.taskmanager.tm.services.task.TaskConverter;

public class ProjectConverter {

    private ProjectConverter() {}

    static Project toProject(CreateProjectDTO dto) {
        return new Project(dto.getName());
    }

    static ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .tasks(TaskConverter.toTaskDTOList(project.getTasks()))
                .build();
    }
}
