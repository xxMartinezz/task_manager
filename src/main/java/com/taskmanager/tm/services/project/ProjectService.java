package com.taskmanager.tm.services.project;

import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.entities.task.Task;
import com.taskmanager.tm.repositories.project.ProjectRepository;
import com.taskmanager.tm.repositories.task.TaskRepository;
import com.taskmanager.tm.services.dto.project.CreateProjectDTO;
import com.taskmanager.tm.services.dto.project.ProjectResponse;
import com.taskmanager.tm.services.exceptions.RequestException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Project createProject(CreateProjectDTO dto) {
        Project project = ProjectConverter.toProject(dto);
        this.projectRepository.save(project);
        log.debug("Created project: {}", project);
        assignToTasks(project, dto.getTasks());
        return project;
    }

    private void assignToTasks(Project project, List<Long> tasks) {
        List<Task> taskList = taskRepository.findAllById(tasks);
        if (taskList.isEmpty()) {
            throw new RequestException("There is no task with these IDs", "Project");
        }
        taskList.stream().forEach(task -> task.setProject(project));
        project.setTasks(taskList);
    }

    @Transactional
    public Page<ProjectResponse> getAllProjects(Pageable pageable) {
        return this.projectRepository.findAll(pageable)
                .map(ProjectConverter::toProjectResponse);
    }

    @Transactional
    public Optional<ProjectResponse> getProjectById(Long id) {
        return this.projectRepository.findById(id).map(ProjectConverter::toProjectResponse);
    }

    @Transactional
    public void deleteProject(Long id) {
        this.projectRepository.findById(id).ifPresent(project -> {
            this.projectRepository.deleteById(id);
            log.debug("Deleted project: {}", project);
        });
    }

}
