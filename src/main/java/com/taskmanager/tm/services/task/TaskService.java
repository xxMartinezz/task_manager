package com.taskmanager.tm.services.task;

import com.taskmanager.tm.entities.task.Task;
import com.taskmanager.tm.enums.Priority;
import com.taskmanager.tm.enums.TaskStatus;
import com.taskmanager.tm.enums.TaskType;
import com.taskmanager.tm.repositories.task.TaskRepository;
import com.taskmanager.tm.services.dto.task.TaskDTO;
import com.taskmanager.tm.services.exceptions.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TaskService {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskDTO taskDTO) {
        Task task = new Task();
        if (taskDTO.getName().length() <= 100) {
            task.setName(taskDTO.getName());
        } else {
            throw new RequestException("The name of the new task is too long", "Task");
        }
        if (taskDTO.getTaskType() != null) {
            task.setTaskType(taskDTO.getTaskType());
        } else {
            task.setTaskType(TaskType.ASSIGNMENT);
        }
        task.setComponent(taskDTO.getComponent());
        if (taskDTO.getDescription() != null && taskDTO.getDescription().length() <= 500) {
            task.setDescription(taskDTO.getDescription());
        } else {
            throw new RequestException("The description of the new task is empty or too long", "Task");
        }
        if (taskDTO.getPriority() != null) {
            task.setPriority(taskDTO.getPriority());
        } else {
            task.setPriority(Priority.MEDIUM);
        }
        task.setEstimatedTime(taskDTO.getEstimatedTime());
        task.setDeadlineDate(taskDTO.getDeadlineDate());
        task.setReportedDate(taskDTO.getReportedDate());
        task.setLastChageDate(taskDTO.getLastChageDate());
        task.setLoggedTime(taskDTO.getLoggedTime());
        if (taskDTO.getTaskStatus() != null) {
            task.setTaskStatus(taskDTO.getTaskStatus());
        } else {
            task.setTaskStatus(TaskStatus.NEW);
        }
        task.setSprint(taskDTO.getSprint());
        task.setProject(taskDTO.getProject());
        if (taskDTO.getReportedUser() != null) {
            task.setReportedUser(taskDTO.getReportedUser());
        } else {
            throw new RequestException("The reportedUser of the new task cannot be empty", "Task");
        }
        task.setAssignedUser(taskDTO.getAssignedUser());
        task.setAttachments(taskDTO.getAttachments());
        this.taskRepository.save(task);
        log.debug("Created task: {}", task);
        return task;
    }

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        return this.taskRepository.findAll(pageable).map(TaskDTO::new);
    }
}
