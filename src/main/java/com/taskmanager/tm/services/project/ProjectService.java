package com.taskmanager.tm.services.project;

import com.taskmanager.tm.entities.project.Project;
import com.taskmanager.tm.repositories.project.ProjectRepository;
import com.taskmanager.tm.services.dto.project.ProjectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {

    private final Logger log = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        this.projectRepository.save(project);
        log.debug("Created project: {}", project);
        return project;
    }

    public Page<ProjectDTO> getAllProjects(Pageable pageable) {
        return this.projectRepository.findAll(pageable).map(ProjectDTO::new);
    }

    public Optional<ProjectDTO> getProjectById(Long id) {
        return this.projectRepository.findById(id).map(ProjectDTO::new);
    }

    public void deleteProject(Long id) {
        this.projectRepository.findById(id).ifPresent(project -> {
            this.projectRepository.deleteById(id);
            log.debug("Deleted project: {}", project);
        });
    }

}
