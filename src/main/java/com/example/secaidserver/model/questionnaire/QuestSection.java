package com.example.secaidserver.model.questionnaire;

import com.example.secaidserver.model.abstractive.Visitable;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataExtractor;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataUpdater;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class QuestSection extends Rated implements Serializable, Visitable {
    private String number;

    private String title;

    private List<QuestSection> subsections;

    private List<Question> questions;

    public QuestSection() {
    }

    public QuestSection(String number, String title, List<QuestSection> subsections, List<Question> questions) {
        this.number = number;
        this.title = title;
        this.subsections = subsections;
        this.questions = questions;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuestSection> getSubsections() {
        return new ArrayList<>(subsections);
    }

    public void setSubsections(List<QuestSection> subsections) {
        this.subsections = subsections;
    }

    public List<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
