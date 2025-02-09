package com.example.secaidserver.model.questionnaire;

import com.example.secaidserver.model.abstractive.Visitable;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataExtractor;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataUpdater;

import java.io.Serializable;
import java.util.Deque;

public class Question extends Rated implements Serializable, Visitable {
    private int number;

    private String text;

    private int responseControlIndex;

    private Response response;

    public Question() {
    }

    public Question(int number, String text, int responseControlIndex, Response response) {
        this.text = text;
        this.number = number;
        this.responseControlIndex = responseControlIndex;
        this.response = response;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getResponseControlIndex() {
        return responseControlIndex;
    }

    public void setResponseControlIndex(int responseControlIndex) {
        this.responseControlIndex = responseControlIndex;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public void collect(final PersistingDataExtractor visitor, QuestionnaireDB questionnaireDB) {
        visitor.collect(this, questionnaireDB);
    }

    @Override
    public void update(final PersistingDataUpdater visitor, Deque<QuestSectionDB> sectionsDB, Deque<ResponseDB> responsesDB) {
        visitor.update(this, sectionsDB, responsesDB);
    }
}
