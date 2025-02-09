package com.example.secaidserver.unitary.controller;

import com.example.secaidserver.DummiesFactory;
import com.example.secaidserver.config.JwtAuthenticationEntryPoint;
import com.example.secaidserver.controller.ProgramController;
import com.example.secaidserver.model.program.ProgramDB;
import com.example.secaidserver.service.ProgramService;
import com.example.secaidserver.service.implementation.UserDetailsServiceImpl;
import com.example.secaidserver.worker.JwtTokenUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test on REST program controller.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProgramController.class)
public class ProgramControllerUnitTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProgramController programController;

    @MockBean
    ProgramService programService;

    @MockBean
    JwtTokenUtil jwtTokenUtil;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testGetAllPrograms() throws Exception {
        List<ProgramDB> programsDB = Arrays.asList(
                new ProgramDB(DummiesFactory.createDummyProgram(0, true)),
                new ProgramDB(DummiesFactory.createDummyProgram(0, true))
        );

        Mockito.when(programService.getAllPrograms()).thenReturn(programsDB);

        mockMvc.perform(get("/program/all"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", Matchers.hasSize(2)))
               .andExpect(jsonPath("$[0].name", Matchers.is(programsDB.get(0).getName())))
               .andExpect(jsonPath("$[1].name", Matchers.is(programsDB.get(1).getName())));
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testGetProgram() {
        // todo
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testSaveProgram() {
        // todo
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testmanagePrograms() {
        // todo
    }

    @Test
    @WithMockUser(username = "user", password = "pwd", roles = "USER")
    public void testGetLastProgramStep() {
        // todo
    }

}
