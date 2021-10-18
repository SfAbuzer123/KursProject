package com.spring.social.springSocial.service.services;

import com.spring.social.springSocial.model.Estimation;

import java.util.List;

public interface EstimationService {
    void create(Estimation estimation, int userId, int taskId);

    List<Estimation> readAll();

    Estimation read(int id);

    boolean update(Estimation estimation, int id);

    boolean delete(int id);

    int getEstimationAVGForTask(int taskId);
}
