package com.example.secaidserver.integration;

import com.example.secaidserver.DummiesFactory;
import com.example.secaidserver.ProgramEqualityCheckers;
import com.example.secaidserver.controller.ProgramController;
import com.example.secaidserver.model.enums.ImplementationTierEnum;
import com.example.secaidserver.model.program.Asset;
import com.example.secaidserver.model.program.NistCoreSubcategory;
import com.example.secaidserver.model.program.Profile;
import com.example.secaidserver.model.program.Program;
import com.example.secaidserver.model.questionnaire.ManageablePayload;
import com.example.secaidserver.repository.test.ActionAnalysisRepository;
import com.example.secaidserver.repository.test.AssetAttributesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProgramControllerIntegrationTests {

    int existingPrograms;
    int existingAssetAttributes;
    int existingActionsAnalysis;
    @Autowired
    private ProgramController programController;
    @Autowired
    private ActionAnalysisRepository actionAnalysisRepository;
    @Autowired
    private AssetAttributesRepository assetAttributesRepository;

    @BeforeEach
    void collectPretestingInfo() {
        // get existing programs, asset attributes and actions analysis
        existingPrograms = programController.getAllPrograms().size();
        existingAssetAttributes = assetAttributesRepository.findAll().size();
        existingActionsAnalysis = actionAnalysisRepository.findAll().size();
    }

    /**
     * Saving a new program.
     */
    @Test
    public void saveProgramTest() {
        // create dummy program
        Program programToSave = DummiesFactory.createDummyProgram(1);
        // save it
        Program savedProgram = programController.saveProgram(programToSave);
        // assert equality with returned value
        ProgramEqualityCheckers.checkNewProgramsEquality(programToSave, savedProgram);
        // delete that created program
        deleteTestCreatedProgram(savedProgram);
    }

    /**
     * Editing an existing program.
     */
    @Test
    public void editProgramTest() {
        // create dummy program
        Program programToSave = DummiesFactory.createDummyProgram(1);
        // save it
        Program savedProgram = programController.saveProgram(programToSave);
        // modify initially saved program
        savedProgram.setName("Edited program");
        Asset savedAsset = savedProgram.getAssets().get(0);
        savedAsset.setName("Edited asset");
        savedAsset.getRiskAssessment().setProbability(1);
        savedAsset.getRegulations().get(0).setDescription("Edited description");
        Profile profile = savedProgram.getTargetProfile();
        profile.setImplementationTier(ImplementationTierEnum.REPEATABLE);
        NistCoreSubcategory core = profile.getNistCoreSubcategoryList().get(0);
        core.setImplementationLevel(2);
        core.getActionAnalysis().setAction("Edited action");
        // edit modified program
        Program editedProgram = programController.saveProgram(savedProgram);
        // assert equality with returned value
        Assertions.assertEquals(savedProgram.getId(), editedProgram.getId());
        ProgramEqualityCheckers.checkNewProgramsEquality(savedProgram, editedProgram);
        // delete that created program
        deleteTestCreatedProgram(editedProgram);
    }

    @Test
    @Transactional
    public void getNonexistentProgramTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            // get nonexistent program
            programController.getProgram(23L);
        });
    }

    /**
     * !!! Works only with enable lazy load no trans property on hibernate.
     */
    @Test
    public void getProgramTest() {
        // create dummy program
        Program programToSave = DummiesFactory.createDummyProgram(1);
        // save it
        Program savedProgram = programController.saveProgram(programToSave);
        // get saved program
        Program getProgram = programController.getProgram(savedProgram.getId());
        // assert equality
        Assertions.assertEquals(savedProgram.getId(), getProgram.getId());
        ProgramEqualityCheckers.checkNewProgramsEquality(savedProgram, getProgram);
        // delete that created program
        deleteTestCreatedProgram(getProgram);
    }

    /**
     * Add an empty program and then edit it.
     */
    @Test
    public void manageProgramsTest() {
        // add an empty program
        ManageablePayload<Program> payload = new ManageablePayload<>();
        Program programToAdd = new Program();
        String dummyEmptyProgramName = "Dummy empty program";
        programToAdd.setName(dummyEmptyProgramName);
        payload.setItemsToAdd(Collections.singletonList(programToAdd));
        List<Program> programsRemained = programController.managePrograms(payload);
        // find added program
        Program addedProgram = null;
        for (Program program : programsRemained) {
            if (program.getName().equals(dummyEmptyProgramName)) {
                addedProgram = program;
            }
        }
        // assert added program
        Assertions.assertNotNull(addedProgram);
        Assertions.assertEquals(dummyEmptyProgramName, addedProgram.getName());
        // edit added program
        String editedDummyEmptyProgramName = "Edited dummy empty program";
        addedProgram.setName(editedDummyEmptyProgramName);
        payload = new ManageablePayload<>();
        payload.setItemsToEdit(Collections.singletonList(addedProgram));
        programsRemained = programController.managePrograms(payload);
        // find edited program
        Program editedProgram = null;
        for (Program program : programsRemained) {
            if (program.getName().equals(editedDummyEmptyProgramName)) {
                editedProgram = program;
            }
        }
        // assert edited program
        Assertions.assertNotNull(editedProgram);
        Assertions.assertEquals(addedProgram.getId(), editedProgram.getId());
        Assertions.assertEquals(editedDummyEmptyProgramName, editedProgram.getName());
        // delete that created program
        deleteTestCreatedProgram(editedProgram);
    }

    @AfterEach
    void testAfterTestingDeletion() {
        Assertions.assertEquals(programController.getAllPrograms().size(), existingPrograms);
        Assertions.assertEquals(assetAttributesRepository.findAll().size(), existingAssetAttributes);
        Assertions.assertEquals(actionAnalysisRepository.findAll().size(), existingActionsAnalysis);
    }

    private void deleteTestCreatedProgram(Program getProgram) {
        ManageablePayload<Program> payload = new ManageablePayload<>();
        payload.setItemsToDelete(Collections.singletonList(getProgram));
        List<Program> programsRemained = programController.managePrograms(payload);
        // assert deletion
        Assertions.assertEquals(programsRemained.size(), existingPrograms);
    }

}
