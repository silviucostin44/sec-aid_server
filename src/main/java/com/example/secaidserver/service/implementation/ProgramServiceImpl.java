package com.example.secaidserver.service.implementation;

import com.example.secaidserver.model.program.Program;
import com.example.secaidserver.model.program.ProgramDB;
import com.example.secaidserver.repository.ProgramRepository;
import com.example.secaidserver.service.ProgramService;
import com.example.secaidserver.worker.ProgramWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramWorker programWorker;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository,
                              ProgramWorker programWorker) {
        this.programRepository = programRepository;
        this.programWorker = programWorker;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProgramDB getProgram(final Long programId) {
        return programRepository.getById(programId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProgramDB saveProgram(final Program program) {
        ProgramDB programDBToSave = new ProgramDB(program);

        return programRepository.save(programDBToSave);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProgramDB> getAllPrograms() {
        return programRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPrograms(final List<Program> itemsToAdd) {
        List<ProgramDB> programsDbToSave = programWorker.buildListOfProgramsDB(itemsToAdd);

        programRepository.saveAll(programsDbToSave);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editPrograms(List<Program> itemsToEdit) {
        for (Program programToEdit : itemsToEdit) {
            ProgramDB programDB = programRepository.findById(programToEdit.getId())
                                                   .orElseThrow(() -> new IllegalArgumentException("Attempt of editing nonexistent program: " + programToEdit.getId()));
            programDB.setName(programToEdit.getName());
            programRepository.save(programDB);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deletePrograms(List<Program> itemsToDelete) {
        programRepository.deleteAllById(itemsToDelete.stream()
                                                     .map(Program::getId)
                                                     .collect(Collectors.toList()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastProgramStep(Long programId) {
        Program program = Program.completeInstance(getProgram(programId));
        // step 6
        if (program.getCurrentProfile() != null
                && program.getCurrentProfile().getNistCoreSubcategoryList()
                          .stream()
                          .anyMatch((core) -> core.getActionAnalysis() != null
                                  && core.getActionAnalysis().getAction() != null)) {
            return 6;
        }
        // step 5
        if (program.getCurrentProfile() != null) {
            return 5;
        }
        // step 4
        if (program.getAssets() != null
                && program.getAssets().stream().anyMatch(asset -> asset.getRiskAssessment() != null)) {
            return 4;
        }
        // step 3
        if (program.getTargetProfile() != null) {
            return 3;
        }
        // step 2
        if (program.getAssets() != null &&
                program.getAssets()
                       .stream()
                       .anyMatch(asset -> !CollectionUtils.isEmpty(asset.getRegulations())
                               || !CollectionUtils.isEmpty(asset.getVulnerabilities())
                               || !CollectionUtils.isEmpty(asset.getThreats()))) {
            return 2;
        }

        return 1;
    }

}
