package com.taskmanager.tm.repositories.user;

import com.taskmanager.tm.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
}
