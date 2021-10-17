package com.spring.social.springSocial.mapper;

import com.spring.social.springSocial.model.DTO.TaskDTO;
import com.spring.social.springSocial.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDTO taskToTaskDTO(Task task);
}
