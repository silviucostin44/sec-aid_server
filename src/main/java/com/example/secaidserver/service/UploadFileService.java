package com.example.secaidserver.service;

import com.example.secaidserver.model.file.SessionFile;
import com.example.secaidserver.model.enums.UploadFileEnum;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UploadFileService {

    /**
     * Uploads a file to the database.
     *
     * @param file the file to store
     * @param fileType the type of the file
     * @throws IOException exception by getBytes on file
     */
    void upload(final MultipartFile file, final UploadFileEnum fileType) throws IOException;

    /**
     * Retrieves a specific file from database.
     *
     * @param fileType the file type
     * @return the file
     */
    SessionFile getFile(final UploadFileEnum fileType);

    /**
     * Retrieves a specific file from database.
     *
     * @param id the file id
     * @return the file
     */
    SessionFile getFile(final String id);

    /**
     * Retrieves a list of all the uploaded files from db.
     *
     * @return the list of files
     */
    List<SessionFile> getAllFiles();

    /**
     * Retrieves a list of all the uploaded files by specific type from db.
     *
     * @param fileType the file type
     * @return the list of files
     */
    List<SessionFile> getAllFilesByType(final UploadFileEnum fileType);

    /**
     * Retrieves the biggest step of the files in db.
     *
     * @return the step.
     */
    int getLastProgramStep();

    /**
     * Deletes all files in the db.
     */
    void deleteAll();

    /**
     * Deletes all files with a specific type in the db.
     *
     * @param fileType the type of the file
     */
    void deleteAllByType(final UploadFileEnum fileType);

    /**
     * Deletes a file with a specific id in the db.
     *
     * @param id file's id.
     */
    void deleteById(final String id);
}
