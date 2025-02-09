package com.example.secaidserver.service.implementation;

import com.example.secaidserver.model.questionnaire.QuestSection;
import com.example.secaidserver.model.questionnaire.Questionnaire;
import com.example.secaidserver.model.questionnaire.QuestionnaireDB;
import com.example.secaidserver.model.questionnaire.Rated;
import com.example.secaidserver.repository.QuestionnaireRepository;
import com.example.secaidserver.service.QuestionnaireService;
import com.example.secaidserver.worker.ClassJsonParser;
import com.example.secaidserver.worker.QuestionnaireWorker;
import org.json.JSONArray;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    private final QuestionnaireRepository questRepository;
    private final QuestionnaireWorker questWorker;

    @Autowired
    public QuestionnaireServiceImpl(QuestionnaireRepository questRepository,
                                    QuestionnaireWorker questWorker) {
        this.questRepository = questRepository;
        this.questWorker = questWorker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QuestSection> readQuestionnaire() throws IOException {
        List<QuestSection> defaultQuestionnaire;
        InputStream reader = new ClassPathResource("static/Questions.json").getInputStream();
        JSONArray questionnaireJson = new JSONArray(new JSONTokener(reader));

        defaultQuestionnaire = ClassJsonParser.parseQuestionnaire(questionnaireJson);
        reader.close();

        return defaultQuestionnaire;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QuestSection> computeRatings(List<QuestSection> questionnaire) {
        for (QuestSection section : questionnaire) {
            questWorker.computeScores(section);
            float rating = (float) section.getSubsections().stream()
                                          .filter(subSection -> subSection.getRating() != null)
                                          .mapToDouble(Rated::getRating)
                                          .average()
                                          .orElse(-1);
            rating = Math.round(rating * 10) / 10f;
            section.setRating(rating > 0 ? rating : null);
        }

        return questionnaire;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public QuestionnaireDB getQuestionnaire(final UUID questId) {
        return questRepository.getById(questId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public QuestionnaireDB saveQuestionnaire(final Questionnaire questionnaire) {
        QuestionnaireDB questToSave = questWorker.buildQuestionnaireDB(questionnaire);

        return questRepository.save(questToSave);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<QuestionnaireDB> getAllQuestionnaires() {
        return questRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addQuestionnaires(List<Questionnaire> itemsToAdd) throws IOException {
        List<QuestSection> defaultQuestSections = readQuestionnaire();
        for (Questionnaire questToAdd : itemsToAdd) {
            questToAdd.setSections(defaultQuestSections);
        }

        questRepository.saveAll(questWorker.buildListOfQuestionnaireDB(itemsToAdd));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editQuestionnaires(final List<Questionnaire> itemsToEdit) {
        for (Questionnaire questToEdit : itemsToEdit) {
            QuestionnaireDB questDB = questRepository.findById(questToEdit.getId())
                                                     .orElseThrow(() -> new IllegalArgumentException("Attempt of editing nonexistent questionnaire: " + questToEdit.getId()));
            questDB.setName(questToEdit.getName());
            questRepository.save(questDB);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteQuestionnaires(final List<Questionnaire> itemsToDelete) {
        questRepository.deleteAllById(itemsToDelete.stream()
                                                   .map(Questionnaire::getId)
                                                   .collect(Collectors.toList())
        );
    }

}
