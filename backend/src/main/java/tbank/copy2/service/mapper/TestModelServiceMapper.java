package tbank.copy2.service.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.service.model.QuestionModel;
import tbank.copy2.service.model.TestModel;
import tbank.copy2.service.repository.TestModelRepository;
import tbank.copy2.service.service.QuestionService;
import tbank.copy2.service.service.UserAnswerService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestModelServiceMapper {
    @Autowired
    UserAnswerService service;
    @Autowired
    QuestionService questionService;
    @Autowired
    TestModelRepository testModelRepository;

    public TestModel toModel(TestModel oldModel, Long sessionId) {
        TestModel model = new TestModel();
        model.setName(oldModel.getName());
        model.setDescription(oldModel.getDescription());
        model.setUserId(oldModel.getUserId());
        model.setVisible(false);
        List<Long> questionIds = service.getAllCorrectIdsBySessionId(sessionId);

        model = testModelRepository.save(model);
        Long testId = model.getId();

        System.out.println("questionIds: " + questionIds);

        List<QuestionModel> questions = oldModel.getQuestions()
                .stream()
                .filter(question -> !questionIds.contains(question.getId()))
                .map(q -> questionService.copyQuestion(q, testId))
                .collect(Collectors.toList());

        System.out.println("questions: " + questions);
        model.setQuestions(questions);
        return model;
    }
}
