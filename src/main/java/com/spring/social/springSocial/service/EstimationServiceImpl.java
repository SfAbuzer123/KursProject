package com.spring.social.springSocial.service;

import com.spring.social.springSocial.model.Estimation;
import com.spring.social.springSocial.repository.EstimationRepository;
import com.spring.social.springSocial.service.services.EstimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EstimationServiceImpl implements EstimationService {
    @Autowired
    private EstimationRepository estimationRepository;

    @Override
    public void create(Estimation estimation, int userId, int taskId) {
        estimation.setUserId(userId);
        estimation.setTaskId(taskId);
        estimationRepository.save(estimation);
    }

    @Override
    public List<Estimation> readAll() {
        return estimationRepository.findAll();
    }

    @Override
    public Estimation read(int id) {
        return estimationRepository.getById(id);
    }

    @Override
    public boolean update(Estimation estimation, int id) {
        if (estimationRepository.existsById(id)) {
            estimation.setId(id);
            estimationRepository.save(estimation);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (estimationRepository.existsById(id)) {
            estimationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public int getEstimationAVGForTask(int taskId) {
        int sum = 0;
        int count = 0;
        for (Estimation estimation: readAll()) {
            if (estimation.getTaskId() == taskId){
                sum += estimation.getEstimation();
                count++;
            }
        }
        if (count != 0)
            return sum/count;
        else
            return 0;
    }
}
