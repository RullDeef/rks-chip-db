package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;
import ru.iovchinnikov.notificationsusers.entity.ExtUser;


import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service(OptimizationDBService.NAME)
public class OptimizationDBServiceBean implements OptimizationDBService {
    @Inject private Persistence persistence;

    @Override
    public void bodyTableOptimizate() {
        try(Transaction tx = persistence.createTransaction()){
            EntityManager em = persistence.getEntityManager();
            Query query1 = em.createNativeQuery("update mobdekbkp_typonominal set has_body='yes' where body_id is not null and has_body = 'notSet'");
            query1.executeUpdate();
            Query query2 = em.createNativeQuery("update mobdekbkp_typonominal set body_id = null where body_id is not null and has_body = 'no'");
            query2.executeUpdate();
            tx.commit();
        }
        System.out.println("Таблица типономинал обнавлена(данные по корпусу) ");
    }

    @Override
    public List<ExtUser> getAndUpdateComponiFromUsers() {
        List<ExtUser> list;
        try(Transaction tx = persistence.createTransaction()){
            EntityManager em = persistence.getEntityManager();
            Query query1 = em.createNativeQuery("update sec_user set company_id = mc.id from mobdekbkp_company mc where mc.name = company_ref or mc.name_short= company_ref");
            query1.executeUpdate();
            Query query2 = em.createNativeQuery("select * from sec_user where company_id is null", ExtUser.class);
            list = query2.getResultList();
            tx.commit();
        }
        System.out.println(list);
        return list;
    }
}