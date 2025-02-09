package com.example.secaidserver.model.file;

import com.example.secaidserver.model.abstractive.FileDB;
import com.example.secaidserver.model.enums.UploadFileEnum;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "session_files")
public class SessionFile extends FileDB implements Serializable {

    private UploadFileEnum fileType;

    public SessionFile() {
    }

    public SessionFile(UploadFileEnum fileType, String name, String type, byte[] data) {
        super(name, type, data);
        this.fileType = fileType;
    }

    public UploadFileEnum getFileType() {
        return fileType;
    }

    public void setFileType(UploadFileEnum fileType) {
        this.fileType = fileType;
    }
}
