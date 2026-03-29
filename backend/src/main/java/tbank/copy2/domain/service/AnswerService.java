package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.domain.repository.AnswerModelRepository;
import tbank.copy2.domain.model.AnswerModel;

import java.util.List;

@Transactional
@Service
public class AnswerService {
    @Autowired
    private AnswerModelRepository repository;

    public List<AnswerModel> getAnswersByQuestionId(Long questionId) {
        return repository.findAllByQuestionId(questionId);
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

