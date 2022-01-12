package com.spring.social.springSocial.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "estimations")
public class Estimation {
    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "estimation_sequence",
            sequenceName = "estimation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "estimation_sequence"
    )
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "task_id")
    private int taskId;
    @Column(name = "estimation")
    private int estimation;

    public Estimation(int userId, int taskId) {
        this.userId = userId;
        this.taskId = taskId;
    }

    public Estimation() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getEstimation() {
        return estimation;
    }

    public void setEstimation(int estimation) {
        this.estimation = estimation;
    }
}
