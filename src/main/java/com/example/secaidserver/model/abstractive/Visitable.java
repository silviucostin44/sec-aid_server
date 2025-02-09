package com.example.secaidserver.model.abstractive;

import com.example.secaidserver.model.questionnaire.QuestSectionDB;
import com.example.secaidserver.model.questionnaire.QuestionnaireDB;
import com.example.secaidserver.model.questionnaire.ResponseDB;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataExtractor;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataUpdater;

import java.util.Deque;

public interface Visitable {
    void collect(final PersistingDataExtractor visitor, QuestionnaireDB questionnaireDB);

    void update(final PersistingDataUpdater visitor, Deque<QuestSectionDB> sectionsDB, Deque<ResponseDB> responsesDB);
}
