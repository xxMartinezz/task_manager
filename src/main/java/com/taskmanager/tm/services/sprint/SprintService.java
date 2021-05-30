package com.taskmanager.tm.services.sprint;

import com.taskmanager.tm.entities.sprint.Sprint;
import com.taskmanager.tm.repositories.sprint.SprintRepository;
import com.taskmanager.tm.services.dto.sprint.SprintDTO;
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
public class SprintService {

    private final Logger log = LoggerFactory.getLogger(SprintService.class);

    @Autowired
    private SprintRepository sprintRepository;

    public Sprint createSprint(SprintDTO sprintDTO) {
        Sprint sprint = new Sprint();
        sprint.setName(sprintDTO.getName());
        this.sprintRepository.save(sprint);
        log.debug("Created sprint: {}", sprint);
        return sprint;
    }

    public Page<SprintDTO> getAllSprints(Pageable pageable) {
        return this.sprintRepository.findAll(pageable).map(SprintDTO::new);
    }

    public Optional<SprintDTO> getSprintById(Long id) {
        return this.sprintRepository.findById(id).map(SprintDTO::new);
    }

    public void deleteSprint(Long id) {
        this.sprintRepository.findById(id).ifPresent(sprint -> {
            this.sprintRepository.deleteById(id);
            log.debug("Deleted sprint: {}", sprint);
        });
    }

}
