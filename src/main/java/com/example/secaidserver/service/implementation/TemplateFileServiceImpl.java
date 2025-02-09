package com.example.secaidserver.service.implementation;

import com.example.secaidserver.model.file.TemplateFile;
import com.example.secaidserver.model.enums.TemplateFileEnum;
import com.example.secaidserver.repository.TemplateFileRepository;
import com.example.secaidserver.service.TemplateFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TemplateFileServiceImpl implements TemplateFileService {

    private final Path root = Paths.get("src/main/resources/templates/programtemplates");

    private final TemplateFileRepository templateFileRepository;

    @Autowired
    public TemplateFileServiceImpl(TemplateFileRepository templateFileRepository) {
        this.templateFileRepository = templateFileRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadAndSaveAll() {
        try {
            Files.walk(this.root, 1)
                    .filter(path -> !path.equals(this.root))
                    .map(this.root::relativize)
                    .map(path -> {
                        TemplateFileEnum templateType = TemplateFileEnum.valueOfFileName(path.getFileName().toString());
                        String fileName = path.getFileName().toString();
                        String contentType = null;
                        byte[] data = new byte[0];
                        try {
                            contentType = Files.probeContentType(path);
                            data = Files.readAllBytes(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return new TemplateFile(templateType, fileName, contentType, data);
                    })
                    .forEach(templateFileRepository::save);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the program template files.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TemplateFile getFile(TemplateFileEnum fileType) {
        return templateFileRepository.getByTemplateType(fileType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resource loadFile(TemplateFileEnum fileType) {
        try {
            Path file = root.resolve(fileType.getFileName());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the template file: " + fileType.getFileName());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
