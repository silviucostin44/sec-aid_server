package com.example.secaidserver.unitary.service;

import com.example.secaidserver.repository.TemplateFileRepository;
import com.example.secaidserver.service.implementation.TemplateFileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TemplateFileServiceUnitTests {
    @InjectMocks
    TemplateFileServiceImpl templateFileService;

    @Mock
    TemplateFileRepository templateFileRepository;

    @Test
    public void getFileTest() {
        // todo
    }

    @Test
    public void loadFileTest() {
        // todo
    }
}
