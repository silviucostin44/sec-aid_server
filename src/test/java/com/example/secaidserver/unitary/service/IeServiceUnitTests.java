package com.example.secaidserver.unitary.service;

import com.example.secaidserver.repository.SessionFileRepository;
import com.example.secaidserver.service.UploadFileService;
import com.example.secaidserver.service.implementation.IeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IeServiceUnitTests {
    @InjectMocks
    IeServiceImpl ieService;

    @Mock
    UploadFileService uploadFileService;

    @Mock
    SessionFileRepository sessionFileRepository;

    @Test
    public void getZipFromListOfFilesTest() {
        // todo:
    }

    @Test
    public void buildProgramJsonTest() {
        // todo:
    }

    @Test
    public void importProgramJsonTest() {
        // todo:
    }
}
