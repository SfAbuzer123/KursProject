package com.spring.social.springSocial.service;

import com.spring.social.springSocial.model.Topic;
import com.spring.social.springSocial.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void create(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public List<Topic> readAll() {
        return topicRepository.findAll();
    }

    @Override
    public Topic read(int id) {
        return topicRepository.getById(id);
    }

    @Override
    public boolean update(Topic topic, int id) {
        if (topicRepository.existsById(id)) {
            topic.setId(id);
            topicRepository.save(topic);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (topicRepository.existsById(id)) {
            topicRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
