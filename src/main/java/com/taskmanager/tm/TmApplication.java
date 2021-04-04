package com.taskmanager.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class TmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmApplication.class, args);
//		EntityManagerFactory factory = Persistence.createEntityManagerFactory("tm_ds"); //name of persistence unit here
//		EntityManager entityManager = factory.createEntityManager();
	}

}
