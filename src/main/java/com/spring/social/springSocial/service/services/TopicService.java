package com.spring.social.springSocial.service.services;

import com.spring.social.springSocial.model.Topic;

import java.util.List;

public interface TopicService {
    void create(Topic topic);
    List<Topic> readAll();
    Topic read(int id);
    boolean update(Topic topic, int id);
    boolean delete(int id);
}
