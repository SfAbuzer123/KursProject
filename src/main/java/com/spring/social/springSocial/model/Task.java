package com.spring.social.springSocial.model;

import lombok.Data;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;

@Data
@Indexed
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    @GeneratedValue()
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "title")
    @Field
    private String title;

    @Column(name = "task_condition")
    @Field
    private String taskCondition;

    @Column(name = "topic")
    @Field
    private String topic;

    @Column(name = "tag1")
    @Field
    private String tag1;

    @Column(name = "tag2")
    @Field
    private String tag2;

    @Column(name = "tag3")
    @Field
    private String tag3;

    @Column(name = "img1")
    private String img1;

    @Column(name = "img2")
    private String img2;

    @Column(name = "img3")
    private String img3;

    @Column(name = "right_answer1")
    private String rightAnswer1;

    @Column(name = "right_answer2")
    private String rightAnswer2;

    @Column(name = "right_answer3")
    private String rightAnswer3;

    @Column(name = "decide")
    private int decide;

    @Column(name = "cloudinary_id1")
    private String cloudinaryId1;

    @Column(name = "cloudinary_id2")
    private String cloudinaryId2;

    @Column(name = "cloudinary_id3")
    private String cloudinaryId3;
}
