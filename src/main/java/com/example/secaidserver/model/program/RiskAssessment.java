package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RiskAssessment implements Serializable {

    private Long id;
    /**
     * Values from 0 to 3 (NONE, LOW, MEDIUM, HIGH).
     */
    private int probability;
    /**
     * Values from 0 to 3 (NONE, LOW, MEDIUM, HIGH).
     */
    private int impact;

    public RiskAssessment(RiskAssessmentDB riskAssessmentDB) {
        this.id = riskAssessmentDB.getId();
        this.probability = riskAssessmentDB.getProbability();
        this.impact = riskAssessmentDB.getImpact();
    }
}
