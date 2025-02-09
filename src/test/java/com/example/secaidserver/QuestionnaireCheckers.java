package com.example.secaidserver;

import com.example.secaidserver.model.questionnaire.QuestSection;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionnaireCheckers {

    public static void assertDefaultQuest(List<QuestSection> quest) {
        assertEquals(quest.size(), 8);
        assertEquals(quest.get(0).getNumber(), "C.");
        assertEquals(quest.get(0).getSubsections().get(0).getSubsections().get(0).getSubsections().size(), 0);
        assertEquals(quest.get(0).getSubsections().get(0).getSubsections().get(0).getQuestions().size(), 6);
        assertEquals(quest.get(7).getSubsections().get(4).getQuestions().get(2).getResponseControlIndex(), 105);
    }
}
