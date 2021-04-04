package com.taskmanager.tm.repositories.user;

import com.taskmanager.tm.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
