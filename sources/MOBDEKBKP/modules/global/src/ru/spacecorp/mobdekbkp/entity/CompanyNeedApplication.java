package ru.spacecorp.mobdekbkp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@NamePattern("%s|number")
@Table(name = "MOBDEKBKP_COMPANY_NEED_APPLICATION")
@Entity(name = "mobdekbkp$CompanyNeedApplication")
public class CompanyNeedApplication extends StandardEntity {
    private static final long serialVersionUID = 3429412630350393835L;

    @Min(1)
    @NotNull
    @Column(name = "NUMBER_")
    protected Integer number;

    @OneToMany(mappedBy = "companyNeedApplication")
    protected List<CompanyNeed> companyNeed;

    @Lob
    @Column(name = "REQUIREMENT")
    protected String requirement;

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setCompanyNeed(List<CompanyNeed> companyNeed) {
        this.companyNeed = companyNeed;
    }

    public List<CompanyNeed> getCompanyNeed() {
        return companyNeed;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getRequirement() {
        return requirement;
    }


}