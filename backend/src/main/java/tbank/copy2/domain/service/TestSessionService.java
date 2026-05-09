package tbank.copy2.domain.service;

import jakarta.validation.constraints.NotNull;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.domain.repository.TestModelRepository;
import tbank.copy2.domain.repository.TestSessionModelRepository;
import tbank.copy2.domain.mapper.TestModelServiceMapper;
import tbank.copy2.domain.mapper.TestSessionModelServiceMapper;
import tbank.copy2.domain.model.*;

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
    private UserService userService;
    @Autowired
    private TestSessionModelServiceMapper sessionMapper;
    @Autowired
    private TestService testService;


    public TestSessionModel startSession(TestSessionModel model) {
        return repository.save(model);
    }

    public TestSessionModel startSession(TestSessionModel model, String shareToken) {
        if (testService.isShareTokenValid(model.getTestId(), shareToken)) {
            return repository.save(model);
        } else {
            throw new IllegalArgumentException("Неверный токен доступа к тесту");
        }
    }

    public TestSessionModel startWrongSession(Long sessionId) {
        TestSessionModel oldSession = repository.getTestSessionById(sessionId);
        System.out.println("oldSession = " + oldSession);
        TestModel newTest = mapper.toModel(testModelRepository.findById(oldSession.getTestId()), sessionId);
        System.out.println("newTest = " + newTest);
        Long userId = oldSession.getUserId();
        newTest = testModelRepository.save(newTest);
        return repository.save(sessionMapper.toSession(newTest, userId));
    }

    public Pair<TestSessionModel, String> startWrongSession(Long sessionId, String shareToken) {
        if (!testService.isShareTokenValid(repository.getTestSessionById(sessionId).getTestId(), shareToken)) {
            throw new IllegalArgumentException("Неверный токен доступа к тесту");
        }
        TestSessionModel oldSession = repository.getTestSessionById(sessionId);
        TestModel newTest = mapper.toModel(testModelRepository.findById(oldSession.getTestId()), sessionId);
        Long userId = oldSession.getUserId();
        newTest = testModelRepository.save(newTest);
        return new Pair<>(repository.save(sessionMapper.toSession(newTest, userId)), newTest.getShareToken());
    }

    public TestSessionResponseModel answerSession(TestSessionAnswerModel aModel, Long sessionId) {
        Boolean saved;
        Boolean isCorrect;
        TestSessionModel model = repository.getTestSessionById(sessionId);
        CheckedAnswerModel answer = answerTypeChecker.check(aModel.getQuestionId()).checkAnswer(aModel.getQuestionId(), aModel.getUserAnswer());
        isCorrect = answer.isTrue();
        if (isCorrect) {
            model.setCorrectCount(model.getCorrectCount() + 1);
            repository.save(model);
        }
        if (model.getUserId() != null) {
            userAnswerService.addAnswer(sessionId, aModel.getQuestionId(), isCorrect);
            userService.addAnswer(isCorrect, model.getUserId());
        }

        saved = true;
        TestSessionResponseModel response = new TestSessionResponseModel();
        response.setCorrectAnswer(answer.getRightAnswers());
        response.setSaved(saved);
        response.setIsCorrect(isCorrect);
        return response;
    }

    public TestSessionResponseModel answerSession(TestSessionAnswerModel aModel, Long sessionId, String shareToken) {
        if (!testService.isShareTokenValid(repository.getTestSessionById(sessionId).getTestId(), shareToken)) {
            throw new IllegalArgumentException("Неверный токен доступа к тесту");
        }

        Boolean saved;
        Boolean isCorrect;
        TestSessionModel model = repository.getTestSessionById(sessionId);
        CheckedAnswerModel answer = answerTypeChecker.check(aModel.getQuestionId()).checkAnswer(aModel.getQuestionId(), aModel.getUserAnswer());
        isCorrect = answer.isTrue();
        if (isCorrect) {
            model.setCorrectCount(model.getCorrectCount() + 1);
            repository.save(model);
        }
        if (model.getUserId() != null) {
            userService.addAnswer(isCorrect, model.getUserId());
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
        boolean testVisible = testModelRepository.findById(model.getTestId()).getVisible();

        model.setFinished_at(LocalDateTime.now());
        if (model.getUserId() != null && testVisible) {
            userService.addActivity(model.getUserId(), model.getTestId(), model.getTestName(), (int) model.getTotalCount(), (int) model.getCorrectCount());
        }
        return repository.save(model);
    }

    public TestSessionModel finishSession(Long sessionId, String shareToken) {
        if (!testService.isShareTokenValid(repository.getTestSessionById(sessionId).getTestId(), shareToken)) {
            throw new IllegalArgumentException("Неверный токен доступа к тесту");
        }
        TestSessionModel model = repository.getTestSessionById(sessionId);

        model.setFinished_at(LocalDateTime.now());
        if (model.getUserId() != null) {
            userService.addActivity(model.getUserId(), model.getTestId(), model.getTestName(), (int) model.getTotalCount(), (int) model.getCorrectCount());
        }
        return repository.save(model);
    }

    public TestSessionModel getSessionByTestIdAndUserId(Long id, Long userId) {
        return repository.getTestSessionByTestIdAndUserId(id, userId);
    }

    public long getTotalCount(@NotNull Long Id) {
        return testModelRepository.findById(Id).getQuestions().size();
    }
}
