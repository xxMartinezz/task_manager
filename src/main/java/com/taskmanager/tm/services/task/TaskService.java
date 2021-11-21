package com.taskmanager.tm.services.task;

import com.taskmanager.tm.entities.attachment.Attachment;
import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.entities.task.Task;
import com.taskmanager.tm.entities.user.User;
import com.taskmanager.tm.repositories.project.ProjectRepository;
import com.taskmanager.tm.repositories.specification.TaskSpecification;
import com.taskmanager.tm.repositories.sprint.SprintRepository;
import com.taskmanager.tm.repositories.task.TaskRepository;
import com.taskmanager.tm.repositories.user.UserRepository;
import com.taskmanager.tm.services.dto.task.CreateTaskDTO;
import com.taskmanager.tm.services.dto.task.PaginatedTaskListDTO;
import com.taskmanager.tm.services.dto.task.TaskDTO;
import com.taskmanager.tm.services.dto.task.TaskFilterDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SprintRepository sprintRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public void createTask(CreateTaskDTO dto) {
        Task task = TaskConverter.toTask(dto);
        assignReportedUser(dto, task);
        assignAssignedUserIfGiven(dto, task);
        assignAttachments(dto, task);
        taskRepository.save(task);
        log.debug("Created task: {}", task);

        assignToSprintIfGiven(dto, task);
        assignToProject(dto, task);
    }

    private void assignToSprintIfGiven(CreateTaskDTO createTaskDTO, Task task) {
        Long sprintId = createTaskDTO.getSprintId();
        if (sprintId != null) {
            assignToSprint(task, sprintId);
        }
    }

    private void assignToSprint(Task task, Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new IllegalArgumentException("Sprint with id " + sprintId + " not found."));
        task.setSprint(sprint);
        sprint.addTask(task);
    }

    private void assignToProject(CreateTaskDTO createTaskDTO, Task task) {
        Project project = projectRepository.findById(createTaskDTO.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + createTaskDTO.getProjectId() + " not found."));
        task.setProject(project);
        project.addTask(task);
    }

    private void assignReportedUser(CreateTaskDTO dto, Task task) {
        User reportedUser = userRepository.findById(dto.getReportedUserId())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + dto.getReportedUserId() + " not found."));
        task.setReportedUser(reportedUser);
    }

    private void assignAssignedUserIfGiven(CreateTaskDTO dto, Task task) {
        Long assignedUserId = dto.getAssignedUserId();
        if (assignedUserId != null) {
            assignAssignedUser(task, assignedUserId);
        }
    }

    private void assignAssignedUser(Task task, Long assignedUserId) {
        User assignedUser = userRepository.findById(assignedUserId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + assignedUserId + " not found."));
        task.setAssignedUser(assignedUser);
    }

    private void assignAttachments(CreateTaskDTO dto, Task task) {
        List<Attachment> attachments = dto.getAttachments().stream()
                .map(TaskConverter::toAttachment)
                .toList();
        task.setAttachments(attachments);
    }

    @Transactional
    public PaginatedTaskListDTO getTasks(Integer pageNumber, Integer pageSize, String sortBy, TaskFilterDTO taskFilterDTO) {
        log.debug("Getting filtered tasks");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
        TaskSpecification taskSpecification = TaskSpecification.builder()
                .filter(taskFilterDTO)
                .build();
        Page<Task> pageResult = taskRepository.findAll(taskSpecification, pageable);
        return PaginatedTaskListDTO.builder()
                .data(TaskConverter.toTaskDTOList(pageResult.getContent()))
                .count(pageResult.getTotalElements())
                .pages(pageResult.getTotalPages())
                .build();
    }

    public Optional<TaskDTO> getTask(Long id) {
        return taskRepository.findById(id).map(TaskConverter::toTaskDTO);
    }
}
