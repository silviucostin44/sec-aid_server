package com.example.secaidserver.integration;


import com.example.secaidserver.controller.QuestionnaireController;
import com.example.secaidserver.model.enums.ResponseCriteriaEnum;
import com.example.secaidserver.model.questionnaire.ManageablePayload;
import com.example.secaidserver.model.questionnaire.QuestSection;
import com.example.secaidserver.model.questionnaire.Questionnaire;
import com.example.secaidserver.model.questionnaire.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.example.secaidserver.QuestionnaireCheckers.assertDefaultQuest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class QuestionnaireControllerIntegrationTests {

    int existingQuests;
    @Autowired
    private QuestionnaireController questionnaireController;

    @BeforeEach
    void collectPretestingInfo() throws IOException {
        existingQuests = Objects.requireNonNull(questionnaireController.getAllQuestionnaires().getBody()).size();
    }

    @Test
    public void defaultQuestTest() throws IOException {
        List<QuestSection> quest = questionnaireController.getDefaultQuestionnaire().getBody();

        assert quest != null;
        assertDefaultQuest(quest);
    }

    @Test
    public void updateQuestRatingsTest() throws IOException {
        List<QuestSection> quest = questionnaireController.getDefaultQuestionnaire().getBody();
        Response response = new Response();
        response.setApproach(ResponseCriteriaEnum.REACTIVE);
        response.setIntegration(ResponseCriteriaEnum.REACTIVE);
        response.setDeployment(ResponseCriteriaEnum.REACTIVE);
        response.setLearning(ResponseCriteriaEnum.REACTIVE);
        assert quest != null;
        quest.get(0).getSubsections().get(0).getSubsections().get(0).getQuestions().get(0).setResponse(response);
        List<QuestSection> quests = questionnaireController.updateQuestRatings(quest).getBody();

        assert quests != null;
        assertEquals(quests.size(), 8);
        assertEquals(quests.get(0).getSubsections().get(0).getSubsections().get(0).getQuestions().get(0).getRating(), 1);
        assertEquals(quests.get(0).getRating(), 1);
    }

    @Test
    public void saveQuestionnaireTest() throws IOException {
        List<QuestSection> defaultQuest = questionnaireController.getDefaultQuestionnaire().getBody();

        Questionnaire dummyQuest = new Questionnaire(UUID.randomUUID(), defaultQuest, "Dummy quest");
        Questionnaire savedQuest = questionnaireController.saveQuestionnaire(dummyQuest).getBody();

        assert savedQuest != null;
        assertEquals(savedQuest.getId(), dummyQuest.getId());
        assertEquals(savedQuest.getName(), dummyQuest.getName());
        assertDefaultQuest(savedQuest.getSections());

        deleteTestCreatedQuest(savedQuest);
    }

    @Test
    public void getQuestionnaireTest() throws IOException {
        List<QuestSection> defaultQuest = questionnaireController.getDefaultQuestionnaire().getBody();

        Questionnaire dummyQuest = new Questionnaire(UUID.randomUUID(), defaultQuest, "Dummy quest");
        Questionnaire savedQuest = questionnaireController.saveQuestionnaire(dummyQuest).getBody();
        assert savedQuest != null;

        Questionnaire gottenQuest = questionnaireController.getQuestionnaire(savedQuest.getId()).getBody();
        assert gottenQuest != null;

        assertEquals(savedQuest.getId(), gottenQuest.getId());
        assertEquals(savedQuest.getName(), gottenQuest.getName());
        assertDefaultQuest(gottenQuest.getSections());

        deleteTestCreatedQuest(savedQuest);
    }

    @Test
    public void getAllQuestionnairesTest() throws IOException {
        List<QuestSection> defaultQuest = questionnaireController.getDefaultQuestionnaire().getBody();

        Questionnaire dummyQuest = new Questionnaire(UUID.randomUUID(), defaultQuest, "Dummy quest");
        Questionnaire savedQuest = questionnaireController.saveQuestionnaire(dummyQuest).getBody();
        assert savedQuest != null;

        int newNumberOfAllQuests = Objects.requireNonNull(questionnaireController.getAllQuestionnaires().getBody()).size();

        assertEquals(existingQuests + 1, newNumberOfAllQuests);

        deleteTestCreatedQuest(savedQuest);
    }

    @Test
    public void manageQuestionnairesTest() throws IOException {
        // add an empty program
        ManageablePayload<Questionnaire> payload = new ManageablePayload<>();
        List<QuestSection> defaultQuest = questionnaireController.getDefaultQuestionnaire().getBody();
        Questionnaire questToAdd = new Questionnaire(UUID.randomUUID(), defaultQuest, "Dummy quest");
        payload.setItemsToAdd(Collections.singletonList(questToAdd));
        List<Questionnaire> questRemained = questionnaireController.manageQuestionnaires(payload).getBody();
        // find added program
        Questionnaire addedQuest = null;
        assert questRemained != null;
        for (Questionnaire quest : questRemained) {
            if (quest.getId().equals(questToAdd.getId())) {
                addedQuest = quest;
            }
        }
        // assert added program
        Assertions.assertNotNull(addedQuest);
        Assertions.assertEquals(questToAdd.getName(), addedQuest.getName());
        // edit added program
        String editedDummyEmptyQuestName = "Edited dummy empty quest";
        addedQuest.setName(editedDummyEmptyQuestName);
        payload = new ManageablePayload<>();
        payload.setItemsToEdit(Collections.singletonList(addedQuest));
        questRemained = questionnaireController.manageQuestionnaires(payload).getBody();
        // find edited program
        Questionnaire editedQuest = null;
        assert questRemained != null;
        for (Questionnaire quest : questRemained) {
            if (quest.getName().equals(editedDummyEmptyQuestName)) {
                editedQuest = quest;
            }
        }
        // assert edited program
        Assertions.assertNotNull(editedQuest);
        Assertions.assertEquals(addedQuest.getId(), editedQuest.getId());
        Assertions.assertEquals(editedDummyEmptyQuestName, editedQuest.getName());
        // delete that created program
        deleteTestCreatedQuest(editedQuest);
    }

    @AfterEach
    void testAfterTestingDeletion() throws IOException {
        Assertions.assertEquals(Objects.requireNonNull(questionnaireController.getAllQuestionnaires().getBody()).size(), existingQuests);
    }

    private void deleteTestCreatedQuest(Questionnaire quest) throws IOException {
        ManageablePayload<Questionnaire> payload = new ManageablePayload<>();
        payload.setItemsToDelete(Collections.singletonList(quest));
        List<Questionnaire> questsRemained = questionnaireController.manageQuestionnaires(payload).getBody();
        // assert deletion
        assert questsRemained != null;
        Assertions.assertEquals(questsRemained.size(), existingQuests);
    }
}
