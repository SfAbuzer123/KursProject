package com.spring.social.springSocial.model.DTO;

import lombok.Data;

@Data
public class TaskDTO {
    private int id;

    private String title;

    private String taskCondition;

    private String topic;

    private String tag1;

    private String tag2;

    private String tag3;

    private String img1;

    private String img2;

    private String img3;

    private int decide;
}
