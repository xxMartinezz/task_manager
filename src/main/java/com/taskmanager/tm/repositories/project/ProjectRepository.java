package com.taskmanager.tm.repositories.project;

import com.taskmanager.tm.entities.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
