package com.example.secaidserver.repository;

import com.example.secaidserver.model.file.TemplateFile;
import com.example.secaidserver.model.enums.TemplateFileEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateFileRepository extends JpaRepository<TemplateFile, String> {

    TemplateFile getByTemplateType(TemplateFileEnum templateType);
}
