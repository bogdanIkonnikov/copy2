package tbank.copy2.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.QuestionModel;
import tbank.copy2.domain.model.TestModel;
import tbank.copy2.domain.repository.TestModelRepository;
import tbank.copy2.domain.service.QuestionService;
import tbank.copy2.domain.service.UserAnswerService;

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
        model.setAccessMode(oldModel.getAccessMode());
        model.setUserId(oldModel.getUserId());
        model.setShareToken(java.util.UUID.randomUUID().toString().substring(0, 20));
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
