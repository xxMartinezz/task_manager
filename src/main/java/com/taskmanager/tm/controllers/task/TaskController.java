package com.taskmanager.tm.controllers.task;

import com.taskmanager.tm.services.dto.task.CreateTaskDTO;
import com.taskmanager.tm.services.dto.task.PaginatedTaskListDTO;
import com.taskmanager.tm.services.dto.task.TaskDTO;
import com.taskmanager.tm.services.dto.task.TaskFilterDTO;
import com.taskmanager.tm.services.exceptions.RequestException;
import com.taskmanager.tm.services.task.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public ResponseEntity<Void> createTask(@Valid @RequestBody CreateTaskDTO taskDTO) {
        log.debug("Creating task: {}", taskDTO);
        taskService.createTask(taskDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<TaskDTO> updateTask(@Valid @RequestBody TaskDTO taskDTO) {
        log.debug("Updating task: {}", taskDTO);
        taskService.updateTask(taskDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PaginatedTaskListDTO> getTasks(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "reportedDate") String sortBy,
            @ModelAttribute TaskFilterDTO taskFilterDTO) {
        PaginatedTaskListDTO paginatedTaskListDTO = taskService.getTasks(pageNumber, pageSize, sortBy, taskFilterDTO);
        return new ResponseEntity<>(paginatedTaskListDTO, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable(name = "id") Long id) {
        Optional<TaskDTO> task = taskService.getTask(id);
        if (!task.isPresent()) {
            throw new RequestException("There is no task with id " + id, "Task");
        }
        return new ResponseEntity<>(task.get(), HttpStatus.OK);
    }
}
