package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class ActionAnalysis implements Serializable {

    private Long id;

    private String subcategory;

    private String action;

    private int costBenefitIndex;

    private int priority;

    public ActionAnalysis(ActionAnalysisDB actionAnalysisDB) {
        this.id = actionAnalysisDB.getId();
        this.subcategory = actionAnalysisDB.getSubcategory();
        this.action = actionAnalysisDB.getAction();
        this.costBenefitIndex = actionAnalysisDB.getCostBenefitIndex();
        this.priority = actionAnalysisDB.getPriority();
    }
}
