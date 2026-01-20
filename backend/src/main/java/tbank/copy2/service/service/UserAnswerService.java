package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.DAO.repository.UserAnswerModelRepository;
import tbank.copy2.service.model.UserAnswerModel;

@Service
public class UserAnswerService {
    @Autowired
    private UserAnswerModelRepository repository;

    public void addAnswer(Long sessionId, Long questionId, boolean isCorrect) {
        UserAnswerModel model = new UserAnswerModel();
        model.setSessionId(sessionId);
        model.setQuestionId(questionId);
        model.setCorrect(isCorrect);
        repository.save(model);
    }
}
