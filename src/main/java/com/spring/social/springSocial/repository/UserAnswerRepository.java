package com.spring.social.springSocial.repository;

import com.spring.social.springSocial.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {
    @Query(value = "DELETE FROM UserAnswer answer WHERE answer.taskId = :id")
    void deleteUserAnswer(@Param("id") int taskId);
}
