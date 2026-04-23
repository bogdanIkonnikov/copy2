package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.domain.model.AnswerModel;
import tbank.copy2.domain.model.TestModel;
import tbank.copy2.domain.repository.QuestionModelRepository;
import tbank.copy2.domain.model.QuestionModel;
import tbank.copy2.domain.repository.TestModelRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class QuestionService {
    @Autowired
    private QuestionModelRepository repository;
    @Autowired
    private TestModelRepository testRepository;
    @Autowired
    private TestService testService;
    @Autowired
    private AnswerService answerService;

    public List<QuestionModel> getQuestionsByTestId(Long testId) {
        List<QuestionModel> models = repository.findAllByTestId(testId);
        return models;
    }
    public List<QuestionModel> getQuestionsByShareToken(String shareToken) {
        TestModel model = testRepository.findByShareToken(shareToken);
        if (model == null) throw new IllegalArgumentException("Неверный токен доступа к тесту");
        List<QuestionModel> models = repository.findAllByTestId(model.getId());
        return models;
    }

    public QuestionModel addQuestion(QuestionModel model) {
        return repository.save(model);
    }

    public QuestionModel getQuestionById(Long questionId) {
        QuestionModel model = repository.findById(questionId);
        return model;
    }
    public QuestionModel getQuestionById(Long questionId, String shareToken) {
        QuestionModel model = repository.findById(questionId);
        if (testService.isShareTokenValid(model.getTestId(), shareToken)) {
             return model;
        } else {
            throw new IllegalArgumentException("Неверный токен доступа к тесту");
        }
    }

    public QuestionModel copyQuestion(QuestionModel model, Long testId) {
        QuestionModel modelCopy = new QuestionModel();
        modelCopy.setTestId(testId);
        modelCopy.setType(model.getType());
        modelCopy.setContent(model.getContent());
        modelCopy = repository.save(modelCopy);
        Long questionId = modelCopy.getId();
        List<AnswerModel> answers = model.getAnswerModels().stream().map(a -> answerService.copyAnswer(a, questionId)).collect(Collectors.toList());
        modelCopy.setAnswerModels(answers);
        return repository.save(modelCopy);
    }
}
