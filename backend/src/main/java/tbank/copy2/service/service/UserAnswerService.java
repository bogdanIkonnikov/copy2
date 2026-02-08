package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.service.repository.UserAnswerModelRepository;
import tbank.copy2.service.model.UserAnswerModel;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
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

    public List<Long> getAllCorrectIdsBySessionId(Long sessionId) {
        return repository.findUserAnswerModelsBySessionId(sessionId)
                .stream()
                .map(m -> m.getCorrect() ? m : null)
                .filter(m -> m != null)
                .map(m -> m.getQuestionId())
                .collect(Collectors.toList());
    }
}
