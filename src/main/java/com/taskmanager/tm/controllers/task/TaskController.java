package com.taskmanager.tm.controllers.task;

import com.taskmanager.tm.services.dto.task.CreateTaskDTO;
import com.taskmanager.tm.services.dto.task.TaskResponse;
import com.taskmanager.tm.services.task.TaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<Void> createTask(@Valid @RequestBody CreateTaskDTO taskDTO) {
        log.debug("Post method, create task: {}", taskDTO);
        taskService.createTask(taskDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks(Pageable pageable) {
        Page<TaskResponse> page = taskService.getAllTasks(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }
}
