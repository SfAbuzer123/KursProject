package com.spring.social.springSocial.service;

import com.spring.social.springSocial.model.UserInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInfoService {
    /**
     * Создает нового клиента
     * @param userInfo - клиент для создания
     */
    void create(UserInfo userInfo);

    /**
     * Возвращает список всех имеющихся клиентов
     * @return список клиентов
     */
    List<UserInfo> readAll();

    /**
     * Возвращает клиента по его ID
     * @param id - ID клиента
     * @return - объект пользователя с заданным ID
     */
    UserInfo read(int id);

    /**
     * Обновляет клиента с заданным ID,
     * в соответствии с переданным клиентом
     * @param userInfo - клиент в соответсвии с которым нужно обновить данные
     * @param id - id клиента которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(UserInfo userInfo, int id);

    /**
     * Удаляет клиента с заданным ID
     * @param id - id клиента, которого нужно удалить
     * @return - true если клиент был удален, иначе false
     */
    boolean delete(int id);

    List<UserInfo> findByEmail(String email);
}
