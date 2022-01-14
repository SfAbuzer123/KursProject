package com.spring.social.springSocial.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_answer")
public class UserAnswer {
    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "user_answer_sequence",
            sequenceName = "user_answer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_answer_sequence"
    )
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
