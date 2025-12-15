package tbank.copy2.service.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.DAO.repository.TestModelRepository;
import tbank.copy2.DAO.repository.TestSessionModelRepository;
import tbank.copy2.service.model.*;

import java.time.LocalDateTime;


@Service
public class TestSessionService {
    @Autowired
    private TestSessionModelRepository repository;
    @Autowired
    private AnswerTypeChecker answerTypeChecker;
    @Autowired
    private TestModelRepository testModelRepository;

    public TestSessionModel startSession(TestSessionModel model) {
        TestSessionModel oldModel = repository.getTestSessionByTestIdAndUserId(model.getTestId(), model.getUserId());
        if (oldModel != null) repository.delete(oldModel);
        return repository.save(model);
    }

    public TestSessionResponseModel answerSession(TestSessionAnswerModel aModel, Long sessionId) {
        Boolean saved;
        Boolean isCorrect;
        TestSessionModel model =  repository.getTestSessionById(sessionId);
        CheckedAnswerModel answer = answerTypeChecker.check(aModel.getQuestionId()).checkAnswer(aModel.getQuestionId(), aModel.getUserAnswer());
        isCorrect = answer.isTrue();
        if (isCorrect) {
            model.setCorrectCount(model.getCorrectCount() + 1);
            //добавить ответ в UserAnswers
            repository.save(model);
        }

        saved = true;
        TestSessionResponseModel response = new TestSessionResponseModel();
        response.setCorrectAnswer(answer.getRightAnswers());
        response.setSaved(saved);
        response.setIsCorrect(isCorrect);
        return response;
    }

    public TestSessionModel getTestSessionStatus(Long sessionId) {
        return repository.getTestSessionById(sessionId);
    }

    public TestSessionModel finishSession(Long sessionId) {
        TestSessionModel model = repository.getTestSessionById(sessionId);
        model.setFinished_at(LocalDateTime.now());
        repository.save(model);
        return repository.save(model);
    }

    public TestSessionModel getSessionByTestIdAndUserId(Long id, Long userId) {
        return repository.getTestSessionByTestIdAndUserId(id, userId);
    }

    public long getTotalCount(@NotNull Long Id) {
        return testModelRepository.findById(Id).getQuestions().size();
    }
}
