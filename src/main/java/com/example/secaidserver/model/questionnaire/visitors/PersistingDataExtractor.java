package com.example.secaidserver.model.questionnaire.visitors;


import com.example.secaidserver.model.questionnaire.*;

public class PersistingDataExtractor {

    private static PersistingDataExtractor instance;

    private PersistingDataExtractor() {
    }

    public static PersistingDataExtractor getInstance() {
        if (instance == null) {
            instance = new PersistingDataExtractor();
        }
        return instance;
    }

    public void collect(final Question question, QuestionnaireDB questionnaireDB) {
        if (question.getResponse() == null) {
            return;
        }
        ResponseDB responseDB = new ResponseDB(questionnaireDB.getId(), question);
        questionnaireDB.addResponse(responseDB);
    }

    public void collect(final QuestSection questSection, QuestionnaireDB questionnaireDB) {
        QuestSectionDB questSectionDB = new QuestSectionDB(questionnaireDB.getId(), questSection);
        questionnaireDB.addQuestsRating(questSectionDB);

        for (QuestSection subsection : questSection.getSubsections()) {
            subsection.collect(this, questionnaireDB);
        }
        for (Question question : questSection.getQuestions()) {
            question.collect(this, questionnaireDB);
        }
    }

}
