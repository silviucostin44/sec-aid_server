package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "action_analysis")
public class ActionAnalysisDB {

    @Id
    @GeneratedValue
    private Long id;

    private String subcategory;

    private String action;

    private int costBenefitIndex;

    private int priority;

    @OneToOne
    @JoinColumn(name = "nistCoreSubcategory_id", referencedColumnName = "id")
    private NistCoreSubcategoryDB nistCoreSubcategoryDB;

    public ActionAnalysisDB(ActionAnalysis actionAnalysis, NistCoreSubcategoryDB nistCoreSubcategoryDB) {
        this.id = actionAnalysis.getId();
        this.subcategory = actionAnalysis.getSubcategory();
        this.action = actionAnalysis.getAction();
        this.costBenefitIndex = actionAnalysis.getCostBenefitIndex();
        this.priority = actionAnalysis.getPriority();
        this.nistCoreSubcategoryDB = nistCoreSubcategoryDB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActionAnalysisDB)) return false;
        ActionAnalysisDB that = (ActionAnalysisDB) o;
        return getId().equals(that.getId()) && getSubcategory().equals(that.getSubcategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSubcategory());
    }
}
