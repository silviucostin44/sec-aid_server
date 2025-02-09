package com.example.secaidserver.integration;

import com.example.secaidserver.controller.FileController;
import com.example.secaidserver.controller.IeController;
import com.example.secaidserver.controller.QuestionnaireController;
import com.example.secaidserver.model.enums.ResponseCriteriaEnum;
import com.example.secaidserver.model.enums.UploadFileEnum;
import com.example.secaidserver.model.questionnaire.QuestSection;
import com.example.secaidserver.model.questionnaire.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IeControllerIntegrationTests {

    private final MockMultipartFile mockFile = new MockMultipartFile("file",
                                                                     "hello.txt",
                                                                     MediaType.TEXT_PLAIN_VALUE,
                                                                     "Hello, World!".getBytes());
    @Autowired
    private IeController ieController;

    @Autowired
    private FileController fileController;

    @Autowired
    private QuestionnaireController questionnaireController;

    @Test
    public void archiveProgramTest() throws IOException {
        byte[] export = ieController.exportProgram().getBody();

        assertNotNull(export);
        assertEquals(22, export.length);

        fileController.uploadFile(UploadFileEnum.ASSETS_INVENTORY.toString(), mockFile).getBody();

        export = ieController.exportProgram().getBody();

        assertNotNull(export);
        assertTrue(export.length > 22);

        fileController.resetSessionDb();
    }

    @Test
    public void exportProgramTest() {
        byte[] export = ieController.exportProgramAsJson().getBody();

        assertNotNull(export);
        assertEquals(25, export.length);

        fileController.uploadFile(UploadFileEnum.ASSETS_INVENTORY.toString(), mockFile).getBody();

        export = ieController.exportProgramAsJson().getBody();

        assertNotNull(export);
        assertTrue(export.length > 25);

        fileController.resetSessionDb();
    }

    @Test
    public void exportQuestionnaireTest() throws IOException {
        List<QuestSection> defaultQuest = questionnaireController.getDefaultQuestionnaire().getBody();
        byte[] export = ieController.exportQuestionnaireAsJson(defaultQuest).getBody();

        assertNotNull(export);
        assertNotNull(defaultQuest);
        assertEquals(21684, export.length);

        Response response = new Response();
        response.setApproach(ResponseCriteriaEnum.REACTIVE);
        response.setIntegration(ResponseCriteriaEnum.REACTIVE);
        response.setDeployment(ResponseCriteriaEnum.REACTIVE);
        response.setLearning(ResponseCriteriaEnum.REACTIVE);
        defaultQuest.get(0).getSubsections().get(0).getSubsections().get(0).getQuestions().get(0).setResponse(response);


        export = ieController.exportQuestionnaireAsJson(defaultQuest).getBody();

        assertNotNull(export);
        assertTrue(export.length > 21684);

        fileController.resetSessionDb();
    }
}
