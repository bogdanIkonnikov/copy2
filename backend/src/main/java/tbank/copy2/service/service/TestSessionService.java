package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.repository.entity.TestSession;
import tbank.copy2.repository.entity.User;
import tbank.copy2.repository.repository.*;
import tbank.copy2.web.dto.answer.CheckedAnswer;
import tbank.copy2.web.mapper.TestSessionMapper;
import tbank.copy2.web.dto.testSession.*;


@Service
public class TestSessionService {
    @Autowired
    private TestSessionRepository testSessionRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestSessionMapper testSessionMapper;
    @Autowired
    private AnswerTypeChecker answerTypeChecker;

    public TestSessionResponse startSession(AddTestSessionRequest request) {
        Test test = testRepository.findById(request.getTestId()).orElse(null);
        User user = userRepository.findById(1L).orElse(null); // заглушка
        TestSession testSession = testSessionMapper.toTestSession(request, test, user);
        testSessionRepository.save(testSession);
        return testSessionMapper.toSessionResponse(testSession);
    }

    public AnswerSessionResponse answerSession(AnswerSessionRequest request, Long sessionId) {
        Boolean saved;
        Boolean isCorrect;
        TestSession session =  testSessionRepository.getTestSessionById(sessionId);
        CheckedAnswer answer = answerTypeChecker.check(request.getQuestionId()).checkAnswer(request.getQuestionId(), request.getUserAnswer());
        isCorrect = answer.isTrue();
        if (isCorrect) {
            session.setCorrectCount(session.getCorrectCount() + 1);
            //добавить ответ в UserAnswers
            testSessionRepository.save(session);
        }

        saved = true;
        AnswerSessionResponse answerSessionResponse = new AnswerSessionResponse();
        answerSessionResponse.setCorrectAnswer(answer.getRightAnswers());
        answerSessionResponse.setSaved(saved);
        answerSessionResponse.setIsCorrect(isCorrect);
        return answerSessionResponse;
    }

    public TestSessionStatusResponse getTestSessionStatus(Long sessionId) {
        TestSession testSession = testSessionRepository.getTestSessionById(sessionId);
        return testSessionMapper.toSessionStatusResponse(testSession);
    }
}
