package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.domain.model.AnswerModel;
import tbank.copy2.domain.repository.QuestionModelRepository;
import tbank.copy2.domain.model.QuestionModel;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class QuestionService {
    @Autowired
    private QuestionModelRepository repository;
    @Autowired
    private AnswerService answerService;

    public List<QuestionModel> getQuestionsByTestId(Long testId) {
        List<QuestionModel> models = repository.findAllByTestId(testId);
        return models;
    }

    public QuestionModel addQuestion(QuestionModel model) {
        return repository.save(model);
    }

    public QuestionModel getQuestionById(Long questionId) {
        QuestionModel model = repository.findById(questionId);
        return model;
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
