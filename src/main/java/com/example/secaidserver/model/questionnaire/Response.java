package com.example.secaidserver.model.questionnaire;

import com.example.secaidserver.model.enums.ResponseCriteriaEnum;

import java.io.Serializable;

public class Response implements Serializable {

    private ResponseCriteriaEnum approach;

    private ResponseCriteriaEnum deployment;

    private ResponseCriteriaEnum learning;

    private ResponseCriteriaEnum integration;

    public Response() {
    }

    public Response(ResponseCriteriaEnum approach, ResponseCriteriaEnum deployment, ResponseCriteriaEnum learning, ResponseCriteriaEnum integration) {
        this.approach = approach;
        this.deployment = deployment;
        this.learning = learning;
        this.integration = integration;
    }

    public Float computeRating() {
        if (approach == null || deployment == null || learning == null || integration == null) {
            return null;
        }
        return (float) Math.round((float) (approach.getRating() + deployment.getRating() + learning.getRating() + integration.getRating()) / 4 * 10) / 10;
    }

    public ResponseCriteriaEnum getApproach() {
        return approach;
    }

    public ResponseCriteriaEnum getDeployment() {
        return deployment;
    }

    public ResponseCriteriaEnum getLearning() {
        return learning;
    }

    public ResponseCriteriaEnum getIntegration() {
        return integration;
    }

    public void setApproach(ResponseCriteriaEnum approach) {
        this.approach = approach;
    }

    public void setDeployment(ResponseCriteriaEnum deployment) {
        this.deployment = deployment;
    }

    public void setLearning(ResponseCriteriaEnum learning) {
        this.learning = learning;
    }

    public void setIntegration(ResponseCriteriaEnum integration) {
        this.integration = integration;
    }
}
