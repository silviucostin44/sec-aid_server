package com.example.secaidserver.model.questionnaire.visitors;


import com.example.secaidserver.model.questionnaire.*;

import java.util.Deque;
import java.util.Objects;

public class PersistingDataUpdater {

    private static PersistingDataUpdater instance;

    private PersistingDataUpdater() {
    }

    public static PersistingDataUpdater getInstance() {
        if (instance == null) {
            instance = new PersistingDataUpdater();
        }
        return instance;
    }

    public void update(Question question, final Deque<QuestSectionDB> sectionsDB, Deque<ResponseDB> responsesDB) {
        ResponseDB responseDB = responsesDB.pollFirst();
        if (responseDB == null) {
            return;
        }
        question.setRating(responseDB.getRating());
        question.setResponse(new Response(responseDB.getApproach(),
                                          responseDB.getDeployment(),
                                          responseDB.getLearning(),
                                          responseDB.getIntegration()));
    }

    public void update(QuestSection questSection, Deque<QuestSectionDB> sectionsDB, final Deque<ResponseDB> responsesDB) {
        questSection.setRating(Objects.requireNonNull(sectionsDB.poll()).getRating());

        for (QuestSection subsection : questSection.getSubsections()) {
            subsection.update(this, sectionsDB, responsesDB);
        }
        for (Question question : questSection.getQuestions()) {
            question.update(this, sectionsDB, responsesDB);
        }
    }
}
