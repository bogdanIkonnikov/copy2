package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.DAO.repository.QuestionModelRepository;
import tbank.copy2.service.model.QuestionModel;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionModelRepository repository;

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
}
