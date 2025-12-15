package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.service.model.TestSessionAnswerModel;
import tbank.copy2.service.model.TestSessionModel;
import tbank.copy2.service.model.TestSessionResponseModel;
import tbank.copy2.service.service.TestSessionService;
import tbank.copy2.web.dto.testSession.*;

import java.time.LocalDateTime;

@Component
public class TestSessionMapper {
    private final TestSessionService testSessionService;

    public TestSessionMapper(TestSessionService testSessionService) {
        this.testSessionService = testSessionService;
    }

    public TestSessionModel toModel(AddTestSessionRequest request){
        TestSessionModel model = new TestSessionModel();
        model.setTestId(request.getTestId());
        model.setUserId(1L); //заменить логикой
        model.setCorrectCount(0);
        model.setTotalCount(testSessionService.getTotalCount(request.getTestId()));
        model.setStarted_at(LocalDateTime.now());
        return model;
    }
    public TestSessionAnswerModel toModel(AnswerSessionRequest request, Long sessionId){
        TestSessionAnswerModel model = new TestSessionAnswerModel();
        model.setSessionId(sessionId);
        model.setUserAnswer(request.getUserAnswer());
        model.setQuestionId(request.getQuestionId());
        return model;
    }
    public AnswerSessionResponse toResponse(TestSessionResponseModel model){
        AnswerSessionResponse response = new AnswerSessionResponse();
        response.setIsCorrect(model.getIsCorrect());
        response.setCorrectAnswer(model.getCorrectAnswer());
        response.setSaved(model.getSaved());
        return response;
    }

    public TestSessionResponse toSessionResponse(TestSessionModel model){
        TestSessionResponse testSessionResponse = new TestSessionResponse();
        testSessionResponse.setSessionId(model.getId());
        testSessionResponse.setTestId(model.getTestId());
        testSessionResponse.setTestName(model.getTestName());
        testSessionResponse.setStarted_at(String.valueOf(model.getStarted_at()));
        return testSessionResponse;
    }
    public TestSessionStatusResponse toSessionStatusResponse(TestSessionModel testSession){
        TestSessionStatusResponse testSessionStatusResponse = new TestSessionStatusResponse();
        testSessionStatusResponse.setTestId(testSession.getTestId());
        testSessionStatusResponse.setProgress(testSession.getCorrectCount());
        testSessionStatusResponse.setQuestionsCount(testSession.getTotalCount());
        testSessionStatusResponse.setFinished_at(testSession.getFinished_at().toString());
        testSessionStatusResponse.setUserId(testSession.getUserId());
        testSessionStatusResponse.setSessionId(testSession.getId());
        return testSessionStatusResponse;
    }
}
