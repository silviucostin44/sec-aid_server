package com.example.secaidserver.unitary.service;

import com.example.secaidserver.DummiesFactory;
import com.example.secaidserver.model.program.ProgramDB;
import com.example.secaidserver.repository.ProgramRepository;
import com.example.secaidserver.service.implementation.ProgramServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProgramServiceUnitTests {
    @InjectMocks
    ProgramServiceImpl programService;

    @Mock
    ProgramRepository programRepository;

    @Test
    public void testGetAllPrograms() {
        List<ProgramDB> expectedProgramsDB = Arrays.asList(
                new ProgramDB(DummiesFactory.createDummyProgram(0, true)),
                new ProgramDB(DummiesFactory.createDummyProgram(0, true))
        );

        when(programRepository.findAll()).thenReturn(expectedProgramsDB);

        List<ProgramDB> programsDB = programService.getAllPrograms();

        assertEquals(expectedProgramsDB.size(), programsDB.size());
        assertEquals(expectedProgramsDB.get(0).getName(), programsDB.get(0).getName());
        assertEquals(expectedProgramsDB.get(1).getName(), programsDB.get(1).getName());

        verify(programRepository, times(1)).findAll();
    }

    @Test
    public void testGetProgram() {
        ProgramDB expectedProgramDB = new ProgramDB(DummiesFactory.createDummyProgram(0, true));

        when(programRepository.getById(expectedProgramDB.getId())).thenReturn(expectedProgramDB);

        ProgramDB programDB = programService.getProgram(expectedProgramDB.getId());

        assertEquals(expectedProgramDB.getName(), programDB.getName());
        assertEquals(expectedProgramDB.getId(), programDB.getId());

        verify(programRepository, times(1)).getById(expectedProgramDB.getId());
    }

//    @Test
//    public void testSaveProgram() {
//        ProgramDB expectedProgramDB = new ProgramDB(DummiesFactory.createDummyProgram(1, false));
//
//        when(programRepository.save(expectedProgramDB)).thenReturn(expectedProgramDB);
//
//        ProgramDB programDB = programService.saveProgram(Program.completeInstance(expectedProgramDB));
//
//        assertEquals(expectedProgramDB.getName(), programDB.getName());
//        assertEquals(expectedProgramDB.getId(), programDB.getId());
//
//        verify(programRepository, times(1)).save(expectedProgramDB);
//    }

    @Test
    public void testGetLastProgramStep() {
        ProgramDB expectedProgramDB = new ProgramDB(DummiesFactory.createDummyProgram(1, false));

        when(programRepository.getById(expectedProgramDB.getId())).thenReturn(expectedProgramDB);

        int lastStep = programService.getLastProgramStep(expectedProgramDB.getId());

        assertEquals(6, lastStep);

        verify(programRepository, times(1)).getById(expectedProgramDB.getId());
    }
}
