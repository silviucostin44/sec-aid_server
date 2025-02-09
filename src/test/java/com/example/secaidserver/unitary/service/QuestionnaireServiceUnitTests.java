package com.example.secaidserver.unitary.service;

import com.example.secaidserver.QuestionnaireCheckers;
import com.example.secaidserver.model.questionnaire.QuestSection;
import com.example.secaidserver.model.questionnaire.Questionnaire;
import com.example.secaidserver.model.questionnaire.QuestionnaireDB;
import com.example.secaidserver.repository.QuestionnaireRepository;
import com.example.secaidserver.service.implementation.QuestionnaireServiceImpl;
import com.example.secaidserver.worker.QuestionnaireWorker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionnaireServiceUnitTests {
    @InjectMocks
    QuestionnaireServiceImpl questionnaireService;

    @Mock
    QuestionnaireRepository questionnaireRepository;

    @Mock
    QuestionnaireWorker questionnaireWorker;

    @Test
    public void testReadQuestionnaire() throws IOException {
        List<QuestSection> quest = questionnaireService.readQuestionnaire();

        QuestionnaireCheckers.assertDefaultQuest(quest);
    }

    @Test
    public void testGetQuestionnaire() {
        UUID expectedQuestId = UUID.randomUUID();
        QuestionnaireDB expectedQuest = new QuestionnaireDB(expectedQuestId, "dummy quest");

        when(questionnaireRepository.getById(expectedQuestId)).thenReturn(expectedQuest);

        QuestionnaireDB questionnaireDB = questionnaireService.getQuestionnaire(expectedQuestId);

        assertEquals(expectedQuestId, questionnaireDB.getId());
        assertEquals("dummy quest", questionnaireDB.getName());

        verify(questionnaireRepository, times(1)).getById(expectedQuestId);
    }

    @Test
    public void testSaveQuestionnaire() throws IOException {
        UUID expectedQuestId = UUID.randomUUID();
        List<QuestSection> expectedQuest = questionnaireService.readQuestionnaire();
        Questionnaire expectedQuestionnaire = new Questionnaire(expectedQuestId, expectedQuest, "dummy quest");
        QuestionnaireDB expectedQuestionnaireDB = new QuestionnaireDB(expectedQuestId, "dummy quest");

        when(questionnaireRepository.save(expectedQuestionnaireDB)).thenReturn(expectedQuestionnaireDB);
        when(questionnaireWorker.buildQuestionnaireDB(expectedQuestionnaire)).thenReturn(expectedQuestionnaireDB);

        QuestionnaireDB questionnaireDB = questionnaireService.saveQuestionnaire(expectedQuestionnaire);

        assertEquals(expectedQuestId, questionnaireDB.getId());
        assertEquals("dummy quest", questionnaireDB.getName());

        verify(questionnaireRepository, times(1)).save(expectedQuestionnaireDB);
    }

    @Test
    public void testGetAllQuestionnaires() {
        UUID expectedQuestId = UUID.randomUUID();
        QuestionnaireDB expectedQuestionnaireDB = new QuestionnaireDB(expectedQuestId, "dummy quest");

        when(questionnaireRepository.findAll()).thenReturn(Collections.singletonList(expectedQuestionnaireDB));

        List<QuestionnaireDB> questionnairesDB = questionnaireService.getAllQuestionnaires();

        assertEquals(1, questionnairesDB.size());
        assertEquals("dummy quest", questionnairesDB.get(0).getName());

        verify(questionnaireRepository, times(1)).findAll();
    }

}
