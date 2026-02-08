package tbank.copy2.service.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.service.repository.TestModelRepository;
import tbank.copy2.service.repository.TestSessionModelRepository;
import tbank.copy2.service.mapper.TestModelServiceMapper;
import tbank.copy2.service.mapper.TestSessionModelServiceMapper;
import tbank.copy2.service.model.*;

import java.time.LocalDateTime;

@Transactional
@Service
public class TestSessionService {
    @Autowired
    private TestSessionModelRepository repository;
    @Autowired
    private AnswerTypeChecker answerTypeChecker;
    @Autowired
    private TestModelRepository testModelRepository;
    @Autowired
    private UserAnswerService userAnswerService;
    @Autowired
    private TestModelServiceMapper mapper;
    @Autowired
    private TestSessionModelServiceMapper sessionMapper;


    public TestSessionModel startSession(TestSessionModel model) {
        System.out.println(model);
        TestSessionModel oldModel = repository.getTestSessionByTestIdAndUserId(model.getTestId(), model.getUserId());
        System.out.println(oldModel);
        if (oldModel != null) repository.deleteById(oldModel.getId());
        return repository.save(model);
    }

    public TestSessionModel startWrongSession(Long sessionId) {
        TestSessionModel oldSession = repository.getTestSessionById(sessionId);
        System.out.println("oldSession = " + oldSession);
        TestModel newTest = mapper.toModel(testModelRepository.findById(oldSession.getTestId()), sessionId);
        System.out.println("newTest = " + newTest);
        Long userId = oldSession.getUserId();
        repository.deleteById(sessionId);
        newTest = testModelRepository.save(newTest);
        return repository.save(sessionMapper.toSession(newTest, userId));
    }

    public TestSessionResponseModel answerSession(TestSessionAnswerModel aModel, Long sessionId) {
        Boolean saved;
        Boolean isCorrect;
        TestSessionModel model =  repository.getTestSessionById(sessionId);
        CheckedAnswerModel answer = answerTypeChecker.check(aModel.getQuestionId()).checkAnswer(aModel.getQuestionId(), aModel.getUserAnswer());
        isCorrect = answer.isTrue();
        if (isCorrect) {
            model.setCorrectCount(model.getCorrectCount() + 1);
            repository.save(model);
        }

        userAnswerService.addAnswer(sessionId, aModel.getQuestionId(), isCorrect);

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
        return repository.save(model);
    }

    public TestSessionModel getSessionByTestIdAndUserId(Long id, Long userId) {
        return repository.getTestSessionByTestIdAndUserId(id, userId);
    }

    public long getTotalCount(@NotNull Long Id) {
        return testModelRepository.findById(Id).getQuestions().size();
    }
}
