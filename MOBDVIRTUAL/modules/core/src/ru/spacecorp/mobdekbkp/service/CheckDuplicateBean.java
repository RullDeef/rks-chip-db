package ru.spacecorp.mobdekbkp.service;

import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import org.springframework.stereotype.Service;
import ru.spacecorp.mobdekbkp.entity.Typonominal;

import javax.inject.Inject;

@Service(CheckDuplicateService.NAME)
public class CheckDuplicateBean implements CheckDuplicateService {
    @Inject
    private Persistence persistence;

    @Override
    public Boolean duplicateTyponominal(Typonominal typonominal) {
        Transaction transaction = persistence.getTransaction();
        Boolean duplicate;
        Typonominal typonominalDuplicate = (Typonominal) persistence.getEntityManager()
                .createQuery("select e from mobdekbkp$Typonominal e where e.name = :typonominalName")
                .setParameter("typonominalName", typonominal.getName())
                .getFirstResult();
        duplicate = typonominalDuplicate != null;
        transaction.commit();
        transaction.end();
        return duplicate;
    }
}