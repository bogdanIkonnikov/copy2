package tbank.copy2.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.service.model.TestModel;
import tbank.copy2.service.model.TestSessionModel;
import tbank.copy2.service.service.TestSessionService;
import tbank.copy2.web.dto.test.AddTestRequest;
import tbank.copy2.web.dto.test.TestResponse;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.web.dto.test.UpdateTestRequest;

import java.util.stream.Collectors;

@Component
public class TestMapper {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private TestSessionService testSessionService;

    public TestResponse toTestResponse(TestModel model) {
        TestSessionModel session = testSessionService.getSessionByTestIdAndUserId(model.getId(), 1L); //заменить логикой
        TestResponse testResponse = new TestResponse();
        testResponse.setId(model.getId());
        testResponse.setName(model.getName());
        testResponse.setDescription(model.getDescription());
        testResponse.setQuestionsCount(model.getQuestions().size());
        if (session == null) {
            testResponse.setProgress(0);
            testResponse.setLastUse(null);
            return testResponse;
        } else {
            testResponse.setProgress((int) session.getCorrectCount());
            testResponse.setLastUse(session.getFinished_at().toString());
        }
        return testResponse;
    }

    public TestModel toModel(AddTestRequest request) {
        TestModel model = new TestModel();
        model.setName(request.getName());
        model.setDescription(request.getDescription());
        model.setUserId(1L); //заменить логикой
        return model;
    }

    public TestModel toModel(UpdateTestRequest request, Long testId) {
        TestModel model = new TestModel();
        model.setName(request.getName());
        model.setDescription(request.getDescription());
        model.setQuestions(request.getQuestions().stream()
                .map(q -> questionMapper.toModel(q, testId)).collect(Collectors.toList()));
        return model;
    }
}
