package com.example.secaidserver.controller;

import com.example.secaidserver.model.questionnaire.ManageablePayload;
import com.example.secaidserver.model.questionnaire.QuestSection;
import com.example.secaidserver.model.questionnaire.Questionnaire;
import com.example.secaidserver.model.questionnaire.QuestionnaireDB;
import com.example.secaidserver.service.QuestionnaireService;
import com.example.secaidserver.worker.QuestionnaireWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Controller managing questionnaire related data.
 */
@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questService;

    private final QuestionnaireWorker questWorker;

    @Autowired
    public QuestionnaireController(QuestionnaireService questService, QuestionnaireWorker questWorker) {
        this.questService = questService;
        this.questWorker = questWorker;
    }

    @GetMapping("/default")
    public ResponseEntity<List<QuestSection>> getDefaultQuestionnaire() throws IOException {
        List<QuestSection> questionnaire = questService.readQuestionnaire();

        return ResponseEntity.ok(questionnaire);
    }

    @PutMapping("/update-rating")
    public ResponseEntity<List<QuestSection>> updateQuestRatings(@RequestBody final List<QuestSection> questionnaire) {
        List<QuestSection> updatedQuestionnaire = questService.computeRatings(questionnaire);

        return ResponseEntity.ok(updatedQuestionnaire);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Questionnaire> getQuestionnaire(@PathVariable final UUID id) throws IOException {
        QuestionnaireDB questionnaireDB = questService.getQuestionnaire(id);

        return ResponseEntity.ok(questWorker.parseQuestionnaireDB(questionnaireDB, questService.readQuestionnaire()));
    }

    @PostMapping("/save")
    public ResponseEntity<Questionnaire> saveQuestionnaire(@RequestBody final Questionnaire questionnaire) throws IOException {
        QuestionnaireDB updatedQuestionnaire = questService.saveQuestionnaire(questionnaire);

        return ResponseEntity.ok(questWorker.parseQuestionnaireDB(updatedQuestionnaire, questService.readQuestionnaire()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Questionnaire>> getAllQuestionnaires() throws IOException {
        List<QuestionnaireDB> questionnairesDB = questService.getAllQuestionnaires();
        List<Questionnaire> questionnaires = questWorker.parseListOfQuestionnaireDB(questionnairesDB,
                                                                                    questService.readQuestionnaire());

        return ResponseEntity.ok(questionnaires);
    }

    @PostMapping("/manage")
    public ResponseEntity<List<Questionnaire>> manageQuestionnaires(@RequestBody final ManageablePayload<Questionnaire> manageablePayload)
            throws IOException {
        questService.deleteQuestionnaires(manageablePayload.getItemsToDelete());
        questService.editQuestionnaires(manageablePayload.getItemsToEdit());
        questService.addQuestionnaires(manageablePayload.getItemsToAdd());
        List<QuestionnaireDB> questionnairesDB = questService.getAllQuestionnaires();
        List<QuestSection> defaultQuest = questService.readQuestionnaire();

        return ResponseEntity.ok(questWorker.parseListOfQuestionnaireDB(questionnairesDB, defaultQuest));
    }
}
