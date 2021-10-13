package com.spring.social.springSocial.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "topics")
public class Topic {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "topic")
    private String topic;
}
