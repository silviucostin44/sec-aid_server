package com.example.secaidserver.service;

import com.example.secaidserver.model.questionnaire.QuestSection;
import com.example.secaidserver.model.questionnaire.Questionnaire;
import com.example.secaidserver.model.questionnaire.QuestionnaireDB;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface QuestionnaireService {

    /**
     * Reads the questionnaire from the resource file.
     *
     * @return the questionnaire as list of sections.
     */
    List<QuestSection> readQuestionnaire() throws IOException;

    /**
     * Computes the ratings in the questionnaire.
     *
     * @param questionnaire the questionnaire.
     * @return the updated questionnaire.
     */
    List<QuestSection> computeRatings(List<QuestSection> questionnaire);

    /**
     * Retrieves a questionnaire from db.
     *
     * @param questId the questionnaire id.
     * @return the questionnaire from db.
     */
    QuestionnaireDB getQuestionnaire(final UUID questId);

    /**
     * Save a questionnaire on db, or update if already exists.
     *
     * @param questionnaire the questionnaire.
     * @return the questionnaire.
     */
    QuestionnaireDB saveQuestionnaire(final Questionnaire questionnaire);

    /**
     * Retrieve all the questionnaires on the db.
     *
     * @return the list of questionnaires.
     */
    List<QuestionnaireDB> getAllQuestionnaires();

    /**
     * Save a list of new default questionnaires on db.
     * These quests have the sections loaded from resource file.
     *
     * @param itemsToAdd the questionnaires to save.
     */
    void addQuestionnaires(List<Questionnaire> itemsToAdd) throws IOException;

    /**
     * Edit the name of a list of questionnaires.
     *
     * @param itemsToEdit the questionnaires to edit.
     */
    void editQuestionnaires(final List<Questionnaire> itemsToEdit);

    /**
     * Deletes a list of questionnaires.
     *
     * @param itemsToDelete the questionnaires to delete.
     */
    void deleteQuestionnaires(final List<Questionnaire> itemsToDelete);
}
