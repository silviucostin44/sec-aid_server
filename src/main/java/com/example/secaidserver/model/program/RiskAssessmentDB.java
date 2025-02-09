package com.example.secaidserver.model.program;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "risk_assessment")
public class RiskAssessmentDB {

    @Id
    @GeneratedValue
    private Long id;

    private int probability;

    private int impact;

    @OneToOne
    @JoinColumn(name = "asset_id")
    private AssetDB asset;

    public RiskAssessmentDB(RiskAssessment riskAssessment, AssetDB assetDB) {
        this.id = riskAssessment.getId();
        this.probability = riskAssessment.getProbability();
        this.impact = riskAssessment.getImpact();
        this.asset = assetDB;
    }
}
