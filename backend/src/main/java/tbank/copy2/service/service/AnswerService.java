package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.service.repository.AnswerModelRepository;
import tbank.copy2.service.model.AnswerModel;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerModelRepository repository;


    public List<AnswerModel> getAnswersByQuestionId(Long questionId) {
        List<AnswerModel> answers = repository.findAllByQuestionId(questionId);
        return answers;
    }

    public AnswerModel addAnswer(AnswerModel answerModel) {
        return repository.save(answerModel);
    }

    public AnswerModel copyAnswer(AnswerModel answerModel, Long questionId) {
        AnswerModel copy = new AnswerModel();
        copy.setQuestionId(questionId);
        copy.setContent(answerModel.getContent());
        copy.setIsCorrect(answerModel.getIsCorrect());
        return repository.save(copy);
    }

}

