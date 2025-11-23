package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.web.dto.testSession.AddTestSessionRequest;
import tbank.copy2.web.dto.testSession.TestSessionResponse;
import tbank.copy2.web.dto.testSession.TestSessionStatusResponse;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.repository.entity.TestSession;
import tbank.copy2.repository.entity.User;

import java.time.LocalDateTime;

@Component
public class TestSessionMapper {
    public TestSession toTestSession(AddTestSessionRequest request, Test test, User user){
        TestSession testSession = new TestSession();
        testSession.setTest(test);
        testSession.setUser(user);
        testSession.setCorrectCount(0);
        testSession.setTotalCount(testSession.getTest().getQuestions().size());
        testSession.setStarted_at(LocalDateTime.now());
        return testSession;
    }
    public TestSessionResponse toSessionResponse(TestSession testSession){
        TestSessionResponse testSessionResponse = new TestSessionResponse();
        testSessionResponse.setSessionId(testSession.getId());
        testSessionResponse.setTestId(testSession.getTest().getId());
        testSessionResponse.setTestName(testSession.getTest().getName());
        testSessionResponse.setStarted_at(String.valueOf(testSession.getStarted_at()));
        return testSessionResponse;
    }
    public TestSessionStatusResponse toSessionStatusResponse(TestSession testSession){
        TestSessionStatusResponse testSessionStatusResponse = new TestSessionStatusResponse();
        testSessionStatusResponse.setTestId(testSession.getTest().getId());
        testSessionStatusResponse.setProgress(testSession.getCorrectCount());
        testSessionStatusResponse.setQuestionsCount(testSession.getTotalCount());
        testSessionStatusResponse.setFinished_at(testSession.getFinished_at().toString());
        testSessionStatusResponse.setUserId(testSession.getUser().getId());
        testSessionStatusResponse.setSessionId(testSession.getId());
        return testSessionStatusResponse;
    }
}
