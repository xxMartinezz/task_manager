package com.taskmanager.tm.repositories.user;

import com.taskmanager.tm.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
    * Repository for the {@link User} entity.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(value ="SELECT * FROM user e WHERE concat(e.name, ' ', e.surname) LIKE %:nameOrSurname%", nativeQuery = true)
    List<User> findUsersByName(@Param("nameOrSurname") String nameOrSurname);

}
