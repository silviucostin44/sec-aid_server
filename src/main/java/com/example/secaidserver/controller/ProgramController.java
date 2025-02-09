package com.example.secaidserver.controller;

import com.example.secaidserver.model.program.Program;
import com.example.secaidserver.model.program.ProgramDB;
import com.example.secaidserver.model.questionnaire.ManageablePayload;
import com.example.secaidserver.service.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller managing program related data.
 */
@Controller
@CrossOrigin("http://localhost:4200")
@RequestMapping("/program")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Program getProgram(@PathVariable final Long id) {
        ProgramDB programDB = programService.getProgram(id);

        return Program.completeInstance(programDB);
    }

    @PostMapping("/save")
    @ResponseBody
    public Program saveProgram(@RequestBody final Program program) {
        ProgramDB programDB = programService.saveProgram(program);

        return Program.completeInstance(programDB);
    }

    @GetMapping("/all")
    @ResponseBody
    public List<Program> getAllPrograms() {
        List<ProgramDB> programsDB = programService.getAllPrograms();

        return parseListOfProgramsDB(programsDB);
    }

    @PostMapping("/manage")
    @ResponseBody
    public List<Program> managePrograms(@RequestBody final ManageablePayload<Program> manageablePayload) {
        programService.deletePrograms(manageablePayload.getItemsToDelete());
        programService.editPrograms(manageablePayload.getItemsToEdit());
        programService.addPrograms(manageablePayload.getItemsToAdd());
        List<ProgramDB> programsDB = programService.getAllPrograms();

        return parseListOfProgramsDB(programsDB);
    }

    @GetMapping("/{programId}/last-step")
    @ResponseBody
    public int getLastProgramStep(@PathVariable final Long programId) {
        return this.programService.getLastProgramStep(programId);
    }

    private List<Program> parseListOfProgramsDB(final List<ProgramDB> programsDB) {
        return programsDB.stream()
                         .map(Program::displayInstance)
                         .collect(Collectors.toList());
    }
}
