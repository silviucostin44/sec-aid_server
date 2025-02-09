package com.example.secaidserver.worker;

import com.example.secaidserver.model.questionnaire.*;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataExtractor;
import com.example.secaidserver.model.questionnaire.visitors.PersistingDataUpdater;
import org.springframework.stereotype.Component;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionnaireWorker {

    /**
     * Computes the score of an entire section and elements included by performing arithmetic average.
     *
     * @param section the section to compute scores for.
     */
    public void computeScores(QuestSection section) {
        for (QuestSection subsection : section.getSubsections()) {
            this.computeScores(subsection);
            float rating;
            if ((subsection.getSubsections().isEmpty())) {
                rating = (float) subsection.getQuestions().stream()
                                           .filter(question -> question.getRating() != null)
                                           .mapToDouble(Rated::getRating)
                                           .average()
                                           .orElse(-1);
            } else {
                rating = (float) subsection.getSubsections().stream()
                                           .filter(subSection -> subSection.getRating() != null)
                                           .mapToDouble(Rated::getRating)
                                           .average()
                                           .orElse(-1);
            }
            rating = Math.round(rating * 10) / 10f;

            subsection.setRating(rating > 0 ? rating : null);
        }
        for (Question question : section.getQuestions()) {
            if (question.getResponse() != null) {
                question.setRating(question.getResponse().computeRating());
            }
        }
    }

    /**
     * Creates a list of questionnaires from a list of questionnaire db objects.
     *
     * @param questionnairesDB the list of db questionnaires to parse from.
     * @return the list of questionnaires.
     */
    public List<Questionnaire> parseListOfQuestionnaireDB(final List<QuestionnaireDB> questionnairesDB, final List<QuestSection> defaultQuest) {
        return questionnairesDB.stream()
                               .map(questionnaireDB -> parseQuestionnaireDB(questionnaireDB, defaultQuest))
                               .collect(Collectors.toList());
    }

    /**
     * Creates a list of questionnaire db objects from a list of questionnaires.
     *
     * @param questionnaires the list of questionnaires to build from.
     * @return the list of questionnaire db objects.
     */
    public List<QuestionnaireDB> buildListOfQuestionnaireDB(final List<Questionnaire> questionnaires) {
        return questionnaires.stream()
                             .map(this::buildQuestionnaireDB)
                             .collect(Collectors.toList());
    }

    /**
     * Creates a Questionnaire object from the DB object.
     *
     * @param questionnaireDB the questionnaire from db.
     * @return the parsed questionnaire.
     */
    public Questionnaire parseQuestionnaireDB(final QuestionnaireDB questionnaireDB, final List<QuestSection> defaultQuest) {
        Deque<QuestSectionDB> questsRatings = new LinkedList<>(questionnaireDB.getQuestsRatings());
        Deque<ResponseDB> responses = new LinkedList<>(questionnaireDB.getResponses());

        // update default quest with persisted data
        PersistingDataUpdater persistingDataUpdater = PersistingDataUpdater.getInstance();
        for (QuestSection section : defaultQuest) {
            section.update(persistingDataUpdater, questsRatings, responses);
        }

        return new Questionnaire(questionnaireDB.getId(),
                                 defaultQuest,
                                 questionnaireDB.getName());
    }

    /**
     * Creates a QuestionnaireDB object from questionnaire model object.
     *
     * @param questionnaire the questionnaire to build from.
     * @return the db questionnaire.
     */
    public QuestionnaireDB buildQuestionnaireDB(final Questionnaire questionnaire) {
        QuestionnaireDB newQuestionnaireDB = new QuestionnaireDB(questionnaire.getId(),
                                                                 questionnaire.getName());

        PersistingDataExtractor persistingDataExtractor = PersistingDataExtractor.getInstance();
        for (QuestSection section : questionnaire.getSections()) {
            section.collect(persistingDataExtractor, newQuestionnaireDB);
        }

        return newQuestionnaireDB;
    }
}
