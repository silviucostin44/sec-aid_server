package com.example.secaidserver.service.implementation;

import com.example.secaidserver.model.enums.UploadFileEnum;
import com.example.secaidserver.model.file.SessionFile;
import com.example.secaidserver.repository.SessionFileRepository;
import com.example.secaidserver.service.IeService;
import com.example.secaidserver.service.UploadFileService;
import com.example.secaidserver.worker.ClassJsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class IeServiceImpl implements IeService {

    private final UploadFileService uploadFileService;

    private final SessionFileRepository sessionFileRepository;

    @Autowired
    public IeServiceImpl(UploadFileService uploadFileService, SessionFileRepository sessionFileRepository) {
        this.uploadFileService = uploadFileService;
        this.sessionFileRepository = sessionFileRepository;
    }

    /**
     * {@inheritDoc}
     */
    public ByteArrayOutputStream getZipFromListOfFiles(final List<SessionFile> files) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);

        for (SessionFile sessionFile : files) {
            ZipEntry zipEntry = new ZipEntry(sessionFile.getName());
            zipOut.putNextEntry(zipEntry);

            zipOut.write(sessionFile.getData());
        }

        zipOut.close();
        return baos;
    }

    /**
     * {@inheritDoc}
     */
    public JSONObject buildProgramJson() {
        List<SessionFile> files = uploadFileService.getAllFiles();
        JSONObject exportObj = new JSONObject();
        JSONArray step7Files = new JSONArray();

        for (SessionFile file : files) {
            if (file.getFileType().getStep() == UploadFileEnum.IMPLEMENTATION_DOC.getStep()) {
                step7Files.put(new JSONObject(file));
            } else {
                exportObj.put(file.getFileType().toString(), new JSONObject(file));
            }
        }
        exportObj.put(UploadFileEnum.IMPLEMENTATION_DOC.toString(), step7Files);

        return exportObj;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void importProgramJson(final JSONObject programJsonObj) throws JsonProcessingException {
        List<SessionFile> importedFiles = this.parseProgramJson(programJsonObj);
        sessionFileRepository.saveAll(importedFiles);
    }

    /**
     * Parses a json to a current program.
     *
     * @param programJsonObj the json object.
     * @return the list of session files.
     */
    private List<SessionFile> parseProgramJson(final JSONObject programJsonObj) throws JsonProcessingException {
        List<SessionFile> files = new ArrayList<>();
        for (UploadFileEnum uploadFileEnum : UploadFileEnum.values()) {
            if (uploadFileEnum != UploadFileEnum.IMPLEMENTATION_DOC) {
                if (programJsonObj.has(uploadFileEnum.toString())) {
                    Object fileAsObject = programJsonObj.get(uploadFileEnum.toString());
                    files.add(ClassJsonParser.parseSessionFile(fileAsObject));
                }
            } else {
                if (programJsonObj.has(UploadFileEnum.IMPLEMENTATION_DOC.toString())) {
                    JSONArray filesAsObject = programJsonObj.getJSONArray(UploadFileEnum.IMPLEMENTATION_DOC.toString());
                    for (Object file : filesAsObject) {
                        files.add(ClassJsonParser.parseSessionFile(file));
                    }
                }
            }
        }

        return files;
    }
}
