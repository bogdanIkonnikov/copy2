package tbank.copy2.domain.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.TestModel;
import tbank.copy2.domain.model.TestSessionModel;

@Component
public class TestSessionModelServiceMapper {
    public TestSessionModel toSession(TestModel test, Long userId) {
        TestSessionModel model = new TestSessionModel();
        model.setUserId(userId);
        model.setTestName(test.getName());
        model.setTestId(test.getId());
        model.setCorrectCount(0);
        model.setTotalCount(test.getQuestions().size());
        return model;
    }
}
