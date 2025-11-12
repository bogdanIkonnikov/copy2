package tbank.copy2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.dto.testSession.*;
import tbank.copy2.entity.TestSession;
import tbank.copy2.repository.*;

import java.time.LocalDateTime;
import java.util.*;

import static tbank.copy2.enums.Type.*;

@Service
public class TestSessionService {
    @Autowired
    private TestSessionRepository testSessionRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public TestSessionResponse startSession(AddTestSessionRequest request) {
        TestSession testSession = new TestSession();
        testSession.setTest(testRepository.findById(request.getTestId()).orElse(null));
        testSession.setUser(userRepository.findById(1L).orElse(null)); //ZAGLUSHKA
        testSession.setCorrectCount(0L);
        testSession.setTotalCount((long) testSession.getTest().getQuestions().size());
        testSession.setStarted_at(LocalDateTime.now());
        testSessionRepository.save(testSession);
        TestSessionResponse testSessionResponse = new TestSessionResponse();
        testSessionResponse.setSessionId(testSession.getId());
        testSessionResponse.setTestId(testSession.getTest().getId());
        testSessionResponse.setTestName(testSession.getTest().getName());
        testSessionResponse.setStarted_at(String.valueOf(testSession.getStarted_at()));
        return testSessionResponse;
    }

    public AnswerSessionResponse answerSession(AnswerSessionRequest request, Long sessionId) {
        Boolean saved = false;
        Boolean isCorrect = false;
        List<Object> correctAnswer = new ArrayList<>();
        TestSession session =  testSessionRepository.getTestSessionById(sessionId);
        if (questionRepository.getQuestionById(request.getQuestionId()).getType() == CHOICE){
            Set<Long> trueAnswers = new HashSet<>(
                    answerRepository.findAllByQuestionId(request.getQuestionId())
                                    .stream().map(answer -> answer.getId())
                                    .toList());
            Set<Long> userAnswers = new HashSet<>(request.getUserAnswer()
                                    .stream().map(o -> (Long) o)
                                    .toList());
            if (trueAnswers.equals(userAnswers)) {
                isCorrect = true;
                correctAnswer = request.getUserAnswer();
                session.setCorrectCount(session.getCorrectCount() + 1);
                //добавить ответ в UserAnswers
                testSessionRepository.save(session);
                saved = true;
            }
        }
        if (questionRepository.getQuestionById(request.getQuestionId()).getType() == INPUT){
            String trueAnswer = answerRepository.findAllByQuestionId(request.getQuestionId()).get(0).getContent();
            String userAnswer = request.getUserAnswer().get(0).toString();
            if (trueAnswer.equals(userAnswer)) {
                isCorrect = true;
                correctAnswer = request.getUserAnswer();
                session.setCorrectCount(session.getCorrectCount() + 1);
                //добавить ответ в UserAnswers
                testSessionRepository.save(session);
                saved = true;
            }
        }
        AnswerSessionResponse answerSessionResponse = new AnswerSessionResponse();
        answerSessionResponse.setCorrectAnswer(correctAnswer);
        answerSessionResponse.setSaved(saved);
        answerSessionResponse.setIsCorrect(isCorrect);
        return answerSessionResponse;
    }

    public TestSessionStatusResponse getTestSessionStatus(Long sessionId) {
        TestSession testSession = testSessionRepository.getTestSessionById(sessionId);
        TestSessionStatusResponse testSessionStatusResponse = new TestSessionStatusResponse();
        testSessionStatusResponse.setTestId(testSession.getTest().getId());
        testSessionStatusResponse.setProgress(testSession.getCorrectCount());
        testSessionStatusResponse.setQuestionsCount(testSession.getTotalCount());
        testSessionStatusResponse.setFinished_at(testSession.getFinished_at().toString());
        testSessionStatusResponse.setUserId(testSession.getUser().getId());
        testSessionStatusResponse.setSessionId(testSession.getId());
        return testSessionStatusResponse;
    }
}
