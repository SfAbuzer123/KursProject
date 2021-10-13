package com.spring.social.springSocial.search;

import com.spring.social.springSocial.model.Task;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public class TaskSearch {
//    @PersistenceContext
//    private EntityManager entityManager;

    public List<Task> search(String text){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("movie_catalog");
        EntityManager entityManager = emf.createEntityManager();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Task.class).get();
        Query query = queryBuilder.keyword().onFields("title", "taskCondition", "topic", "tag1", "tag2", "tag3").matching(text).createQuery();
        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Task.class);
        @SuppressWarnings("unchecked")
        List<Task> result = jpaQuery.getResultList();
        return result;
    }
}
