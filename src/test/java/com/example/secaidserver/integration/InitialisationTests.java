package com.example.secaidserver.integration;

import com.example.secaidserver.controller.ProgramController;
import com.example.secaidserver.repository.ProgramRepository;
import com.example.secaidserver.service.ProgramService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class InitialisationTests {

    @Autowired
    ProgramController programController;

    @Autowired
    ProgramService programService;

    @Autowired
    ProgramRepository programRepository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(programController);
        Assertions.assertNotNull(programService);
        Assertions.assertNotNull(programRepository);
    }

    /**
     * Utility test for failing test situation when cleaning is needed.
     */
    @Test
    void errorDeletionRoutine() {
        programRepository.deleteAll();
    }
}
