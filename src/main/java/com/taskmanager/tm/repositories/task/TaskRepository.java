package com.taskmanager.tm.repositories.task;

import com.taskmanager.tm.entities.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
