package tbank.copy2.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.service.model.QuestionModel;
import tbank.copy2.service.model.TestModel;
import tbank.copy2.service.service.UserAnswerService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestModelServiceMapper {
    @Autowired
    UserAnswerService service;

    public TestModel toModel(TestModel oldModel, Long sessionId) {
        TestModel model = new TestModel();
        model.setName(oldModel.getName());
        model.setDescription(oldModel.getDescription());
        model.setUserId(oldModel.getUserId());
        List<Long> questionIds = service.getAllWrongIdsBySessionId(sessionId);
        List<QuestionModel> questions = oldModel.getQuestions().stream().filter(question -> questionIds.contains(question.getId())).collect(Collectors.toList());
        model.setQuestions(questions);
        return model;
    }
}
