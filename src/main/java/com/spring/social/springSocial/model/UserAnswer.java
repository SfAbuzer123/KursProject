package com.spring.social.springSocial.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_answers")
public class UserAnswer {
    @Id
    @Column(name = "id")
    @GeneratedValue()
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "task_id")
    private int taskId;
    @Column(name = "decided")
    private int decided;

    public UserAnswer(int userId, int taskId, int decided) {
        this.userId = userId;
        this.taskId = taskId;
        this.decided = decided;
    }

    public UserAnswer() {

    }
}
