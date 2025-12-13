package tbank.copy2.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.service.model.TestModel;
import tbank.copy2.web.dto.test.AddTestRequest;
import tbank.copy2.web.dto.test.TestResponse;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.web.dto.test.UpdateTestRequest;

import java.util.stream.Collectors;

@Component
public class TestMapper {
    @Autowired
    private QuestionMapper questionMapper;

    public TestResponse toTestResponse(TestModel model) {
        TestResponse testResponse = new TestResponse();
        testResponse.setId(model.getId());
        testResponse.setName(model.getName());
        testResponse.setDescription(model.getDescription());
        testResponse.setProgress(14); //заменить логикой
        testResponse.setLastUse("10.10.2025"); //заменить логикой
        testResponse.setQuestionsCount(model.getQuestions().size());
        return testResponse;
    }

    public TestModel toModel(AddTestRequest request) {
        TestModel model = new TestModel();
        model.setName(request.getName());
        model.setDescription(request.getDescription());
        model.setUserId(request.getUserId());
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
