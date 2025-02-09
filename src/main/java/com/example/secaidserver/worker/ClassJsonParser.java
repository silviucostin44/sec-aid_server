package com.example.secaidserver.worker;

import com.example.secaidserver.model.file.SessionFile;
import com.example.secaidserver.model.questionnaire.QuestSection;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that offers class parse from json object.
 */
public class ClassJsonParser {

    /**
     * Parses the QuestSection class.
     *
     * @param sectionJson the section object.
     * @return the new QuestSection object.
     * @throws JsonProcessingException mapper reading value exception.
     */
    public static QuestSection parseSection(final Object sectionJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(sectionJson.toString(), QuestSection.class);
    }

    /**
     * Parses the SessionFile class.
     *
     * @param fileJson the file object.
     * @return the new SessionFile object.
     * @throws JsonProcessingException mapper reading value exception.
     */
    public static SessionFile parseSessionFile(final Object fileJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(fileJson.toString(), SessionFile.class);
    }

    /**
     * Parses a questionnaire.
     *
     * @param questionnaireJson the questionnaire as json.
     * @return the questionnaire as list of sections.
     */
    public static List<QuestSection> parseQuestionnaire(final JSONArray questionnaireJson) {
        List<QuestSection> questionnaire = new ArrayList<>();
        questionnaireJson.forEach(section -> {
            try {
                questionnaire.add(ClassJsonParser.parseSection(section));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return questionnaire;
    }


}
