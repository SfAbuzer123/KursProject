package com.spring.social.springSocial.repository;

import com.spring.social.springSocial.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
