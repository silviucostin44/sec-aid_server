package com.example.secaidserver.model.questionnaire;

import com.example.secaidserver.model.enums.ResponseCriteriaEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(ResponseDbPK.class)
@Table(name = "response")
public class ResponseDB {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID questId;

    @Id
    private int globalIndex;

    private Float rating;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionnaireDB questionnaire;

    private ResponseCriteriaEnum approach;

    private ResponseCriteriaEnum deployment;

    private ResponseCriteriaEnum learning;

    private ResponseCriteriaEnum integration;

    public ResponseDB(UUID questId, Question question) {
        this.questId = questId;
        this.globalIndex = question.getResponseControlIndex();
        this.rating = question.getRating();
        this.approach = question.getResponse().getApproach();
        this.deployment = question.getResponse().getDeployment();
        this.learning = question.getResponse().getLearning();
        this.integration = question.getResponse().getIntegration();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResponseDB)) return false;
        ResponseDB that = (ResponseDB) o;
        return getGlobalIndex() == that.getGlobalIndex() && getQuestId().equals(that.getQuestId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestId(), getGlobalIndex());
    }
}
