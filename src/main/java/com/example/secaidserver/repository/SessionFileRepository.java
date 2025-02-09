package com.example.secaidserver.repository;

import com.example.secaidserver.model.file.SessionFile;
import com.example.secaidserver.model.enums.UploadFileEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionFileRepository extends JpaRepository<SessionFile, String> {

    Optional<SessionFile> findByFileType(UploadFileEnum fileType);

    List<SessionFile> findAllByFileType(UploadFileEnum fileType);

    void deleteAllByFileType(UploadFileEnum fileType);

}
