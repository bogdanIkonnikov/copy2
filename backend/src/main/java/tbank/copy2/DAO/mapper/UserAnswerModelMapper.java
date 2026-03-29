package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.infrastructure.persistence.entity.UserAnswer;
import tbank.copy2.infrastructure.persistence.repository.QuestionRepository;
import tbank.copy2.infrastructure.persistence.repository.TestSessionRepository;
import tbank.copy2.domain.model.UserAnswerModel;

@Component
public class UserAnswerModelMapper {
    @Autowired
    private TestSessionRepository testSessionRepository;
    @Autowired
    private QuestionRepository questionRepository;

    public UserAnswerModel toModel(UserAnswer userAnswer) {
        UserAnswerModel model = new UserAnswerModel();
        model.setId(userAnswer.getId());
        model.setCorrect(userAnswer.getCorrect());
        model.setQuestionId(userAnswer.getQuestion().getId());
        model.setCreated_at(userAnswer.getCreated_at());
        model.setSessionId(userAnswer.getSession().getId());
        return model;
    }

    public UserAnswer toEntity(UserAnswerModel model) {
        UserAnswer entity = new UserAnswer();
        entity.setId(model.getId());
        entity.setCorrect(model.getCorrect());
        entity.setSession(testSessionRepository.findById(model.getSessionId()).orElse(null));
        System.out.println("model = " + model);
        System.out.println("session%%%% " + entity.getSession());
        entity.setCreated_at(model.getCreated_at());
        entity.setQuestion(questionRepository.findById(model.getQuestionId()).orElse(null));
        return entity;
    }
}
