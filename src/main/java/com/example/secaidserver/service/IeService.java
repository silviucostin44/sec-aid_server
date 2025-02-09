package com.example.secaidserver.service;

import com.example.secaidserver.model.file.SessionFile;
import com.example.secaidserver.model.questionnaire.QuestSection;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface IeService {

    /**
     * Creates a zip file containing a bunch of files.
     *
     * @param files the list of files to be included.
     * @return the zip as a byte array
     * @throws IOException from zip operations
     */
    ByteArrayOutputStream getZipFromListOfFiles(final List<SessionFile> files) throws IOException;

    /**
     * Builds a json that describes the program of the current session.
     *
     * @return the json object resulted.
     */
    JSONObject buildProgramJson();

    /**
     * Parses the imported json and saves the data.
     *
     * @param programJsonObj the imported json obj.
     */
    void importProgramJson(final JSONObject programJsonObj) throws JsonProcessingException;
}
