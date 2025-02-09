package com.example.secaidserver.service;

import com.example.secaidserver.model.file.TemplateFile;
import com.example.secaidserver.model.enums.TemplateFileEnum;
import org.springframework.core.io.Resource;

public interface TemplateFileService {

//    /**
//     * Initialisation of template file directory.
//     */
//    void init();

    /**
     * Loads all the template files from directory and stores them in the DB.
     */
    void loadAndSaveAll();

    /**
     * Retrieves a file from DB for download.
     *
     * @param fileType the type of the file.
     * @return the file
     */
    TemplateFile getFile(TemplateFileEnum fileType);

    /**
     * Loads and retrieves a file from directory.
     *
     * @param fileType the file type
     * @return the file
     */
    Resource loadFile(TemplateFileEnum fileType);
}
