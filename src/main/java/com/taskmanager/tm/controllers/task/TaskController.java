package com.taskmanager.tm.controllers.task;

import com.taskmanager.tm.entities.task.Task;
import com.taskmanager.tm.services.dto.task.TaskDTO;
import com.taskmanager.tm.services.exceptions.RequestException;
import com.taskmanager.tm.services.task.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/api")
public class TaskController {

    private final Logger log = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<String> createTask(@Valid @RequestBody TaskDTO taskDTO) throws URISyntaxException {
        log.debug("Post method, create task: {}", taskDTO);
        if (taskDTO.getId() != null) {
            throw new RequestException("The new task cannot have an ID", "Task");
        } else {
            Task newTask = this.taskService.createTask(taskDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("New user: " + newTask.toString());
        }
    }

    @GetMapping("/tasks/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks(Pageable pageable) {
        Page<TaskDTO> page = this.taskService.getAllTasks(pageable);
        //
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }
}
