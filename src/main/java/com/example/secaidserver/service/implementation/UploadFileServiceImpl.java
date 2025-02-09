package com.example.secaidserver.service.implementation;

import com.example.secaidserver.model.file.SessionFile;
import com.example.secaidserver.model.enums.UploadFileEnum;
import com.example.secaidserver.repository.SessionFileRepository;
import com.example.secaidserver.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class UploadFileServiceImpl implements UploadFileService {

    private final SessionFileRepository sessionFileRepository;

    @Autowired
    public UploadFileServiceImpl(SessionFileRepository sessionFileRepository) {
        this.sessionFileRepository = sessionFileRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(final MultipartFile file, final UploadFileEnum fileType) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        SessionFile newFile = new SessionFile(fileType, fileName, file.getContentType(), file.getBytes());

        sessionFileRepository.save(newFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionFile getFile(final UploadFileEnum fileType) {
        return sessionFileRepository.findByFileType(fileType).orElseThrow(() -> {
            throw new IllegalArgumentException("There has not been uploaded a file of type: " + fileType);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionFile getFile(final String id) {
        return sessionFileRepository.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("There is no file with id: " + id);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SessionFile> getAllFiles() {
        return sessionFileRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SessionFile> getAllFilesByType(final UploadFileEnum fileType) {
        return sessionFileRepository.findAllByFileType(fileType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastProgramStep() {
        return getAllFiles().stream().mapToInt(file -> file.getFileType().getStep()).max().orElse(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll() {
        sessionFileRepository.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteAllByType(final UploadFileEnum fileType) {
        sessionFileRepository.deleteAllByFileType(fileType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(final String id) {
        sessionFileRepository.deleteById(id);
    }

}

