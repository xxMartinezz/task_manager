package com.taskmanager.tm.controllers.project;

import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.services.dto.project.CreateProjectDTO;
import com.taskmanager.tm.services.dto.project.ProjectResponse;
import com.taskmanager.tm.services.exceptions.RequestException;
import com.taskmanager.tm.services.project.ProjectService;
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
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<String> createProject(@Valid @RequestBody CreateProjectDTO dto) {
        log.debug("Post method, create project: {}", dto);
            Project newProject = this.projectService.createProject(dto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("New project: " + newProject.toString());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponse>> getAllProjects(Pageable pageable) {
        Page<ProjectResponse> page = this.projectService.getAllProjects(pageable);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable(name = "id") Long id) {
        Optional<ProjectResponse> project = this.projectService.getProjectById(id);
        if (project.isEmpty()) {
            throw new RequestException("There is no project with ID " + id + " ", "Project");
        }
        return new ResponseEntity<>(project.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable(name = "id") Long id) {
        this.projectService.deleteProject(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Delete method, deleted project with id: " + id);
    }

}
