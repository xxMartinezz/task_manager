package com.taskmanager.tm.controllers.task;

import com.taskmanager.tm.services.dto.task.CreateTaskDTO;
import com.taskmanager.tm.services.dto.task.PaginatedTaskListDTO;
import com.taskmanager.tm.services.dto.task.TaskFilterDTO;
import com.taskmanager.tm.services.task.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("")
    public ResponseEntity<Void> createTask(@Valid @RequestBody CreateTaskDTO taskDTO) {
        log.debug("Post method, create task: {}", taskDTO);
        taskService.createTask(taskDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
}
