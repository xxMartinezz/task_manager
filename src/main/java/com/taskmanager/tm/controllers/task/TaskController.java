package com.taskmanager.tm.controllers.task;

import com.taskmanager.tm.entities.task.Task;
import com.taskmanager.tm.services.dto.task.TaskDTO;
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
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        log.debug("Post method, create task: {}", taskDTO);
        Task newTask = this.taskService.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body("New user: " + newTask.toString());
    }

    @GetMapping("/tasks/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(Pageable pageable) {
        Page<TaskDTO> page = this.taskService.getAllTasks(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }
}
