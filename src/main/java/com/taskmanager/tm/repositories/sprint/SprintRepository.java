package com.taskmanager.tm.repositories.sprint;

import com.taskmanager.tm.entities.sprint.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
