package com.example.secaidserver.model.file;

import com.example.secaidserver.model.abstractive.FileDB;
import com.example.secaidserver.model.enums.TemplateFileEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "template_files")
public class TemplateFile extends FileDB {

    @Column(unique = true)
    private TemplateFileEnum templateType;

    public TemplateFile() {
    }

    public TemplateFile(TemplateFileEnum templateType, String name, String type, byte[] data) {
        super(name, type, data);
        this.templateType = templateType;
    }

    public TemplateFileEnum getTemplateType() {
        return templateType;
    }

    public void setTemplateType(TemplateFileEnum templateType) {
        this.templateType = templateType;
    }
}
