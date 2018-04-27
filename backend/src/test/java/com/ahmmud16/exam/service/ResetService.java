package com.ahmmud16.exam.service;

import com.ahmmud16.exam.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
@Transactional
public class ResetService {

    @Autowired
    private EntityManager em;

    public void resetDatabase(){

        deleteEntityById("user_roles");

        deleteEntities(User.class);
    }

    private void deleteEntities(Class<?> entity){

        if(entity == null || entity.getAnnotation(Entity.class) == null){
            throw new IllegalArgumentException("Invalid non-entity class");
        }

        String name = entity.getSimpleName();

        /*
            Note: we passed as input a Class<?> instead of a String to
            avoid SQL injection. However, being here just test code, it should
            not be a problem. But, as a good habit, always be paranoiac about
            security, above all when you have code that can delete the whole
            database...
         */

        Query query = em.createQuery("delete from " + name);
        query.executeUpdate();
    }

    //Have to use native SQL for ElementCollection
    private void deleteEntityById(String tableName) {
        Query query = em.createNativeQuery("delete from " + tableName);
        query.executeUpdate();
    }

}
