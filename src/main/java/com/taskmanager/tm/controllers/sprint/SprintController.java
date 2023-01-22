package com.taskmanager.tm.controllers.sprint;

import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.services.dto.sprint.SprintDTO;
import com.taskmanager.tm.services.exceptions.RequestException;
import com.taskmanager.tm.services.sprint.SprintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @PostMapping("")
    public ResponseEntity<String> createSprint(@Valid @RequestBody SprintDTO sprintDTO) {
        log.debug("Post method, create sprint: {}", sprintDTO);
        if (sprintDTO.getId() != null) {
            throw new RequestException("The new sprint cannot have an ID", "Sprint");
        } else {
            Sprint newSprint = this.sprintService.createSprint(sprintDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("New sprint: " + newSprint.toString());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<SprintDTO>> getAllSprints(Pageable pageable) {
        Page<SprintDTO> page = this.sprintService.getAllSprints(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintDTO> getSprintById(@PathVariable(name = "id") Long id) {
        Optional<SprintDTO> sprint = this.sprintService.getSprintById(id);
        if (sprint.isEmpty()) {
            throw new RequestException("There is no sprint with ID " + id + " ", "Sprint");
        }
        return new ResponseEntity<>(sprint.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSprint(@PathVariable(name = "id") Long id) {
        this.sprintService.deleteSprint(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Delete method, deleted sprint with id: " + id);
    }

}
