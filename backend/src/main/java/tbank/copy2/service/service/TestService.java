package tbank.copy2.service.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.repository.entity.Answer;
import tbank.copy2.repository.entity.Question;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.repository.repository.AnswerRepository;
import tbank.copy2.repository.repository.QuestionRepository;
import tbank.copy2.web.dto.answer.UpdateAnswerRequest;
import tbank.copy2.web.dto.question.UpdateQuestionRequest;
import tbank.copy2.web.dto.test.UpdateTestRequest;
import tbank.copy2.web.mapper.QuestionMapper;
import tbank.copy2.web.mapper.TestMapper;
import tbank.copy2.repository.repository.TestRepository;
import tbank.copy2.web.dto.test.AddTestRequest;
import tbank.copy2.web.dto.test.TestResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestMapper testMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionRepository questionRepository;

    public List<TestResponse> getTests() {
        List<Test> tests = testRepository.findAll();
        List<TestResponse> testResponses= new ArrayList<>();
        for (Test test : tests) {
            testResponses.add(testMapper.toTestResponse(test));
        }
        return testResponses;
    }

    public TestResponse addTest(AddTestRequest request) {
        Test savedTest = testRepository.save(testMapper.toEntity(request));
        return testMapper.toTestResponse(savedTest);
    }

    public TestResponse getTestById(Long id) {
        Test test = testRepository.findById(id).orElse(null);
        return testMapper.toTestResponse(test);
    }

    protected void setNewAnswers(Question question, List<UpdateAnswerRequest> answers){
        if (question.getId() == null) {
            question = questionRepository.save(question);
            questionRepository.flush();
        }

        List<Long> answerIdsFromRequest = new ArrayList<>();
        for (UpdateAnswerRequest uAnswer : answers) {
            if (uAnswer.getAnswerId() != null) {
                answerIdsFromRequest.add(uAnswer.getAnswerId());
            }
        }
        System.out.println("Айдишники ответов из реквеста: " + answerIdsFromRequest);

        question.getAnswers().removeIf(answer ->
                !answerIdsFromRequest.contains(answer.getId())
        );
        answerRepository.flush();

        for (UpdateAnswerRequest uAnswer : answers) {
            Answer currentAnswer;
            boolean isNewAnswer = uAnswer.getAnswerId() == null;

            if (!isNewAnswer) {
                currentAnswer = answerRepository.findById(uAnswer.getAnswerId())
                        .orElseThrow(() -> new EntityNotFoundException("Answer not found"));
                currentAnswer.setContent(uAnswer.getContent());
                currentAnswer.setIsCorrect(uAnswer.getIsCorrect());
            } else {
                currentAnswer = new Answer();
                currentAnswer.setContent(uAnswer.getContent());
                currentAnswer.setIsCorrect(uAnswer.getIsCorrect());
                currentAnswer.setQuestion(question);
            }

            answerRepository.save(currentAnswer);
        }

    }

    @Transactional
    public boolean updateTest(UpdateTestRequest request, Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new EntityNotFoundException("Test not found"));

        test.setName(request.getName());
        test.setDescription(request.getDescription());

        List<Question> questionsFromRequest = new ArrayList<>();

        for (UpdateQuestionRequest uQuestion : request.getQuestions()) {
            Question currentQuestion;
            boolean isExistingQuestion = uQuestion.getQuestionId() != null && uQuestion.getQuestionId() > 0;

            if (isExistingQuestion) {
                currentQuestion = questionRepository.findById(uQuestion.getQuestionId())
                        .orElseThrow(() -> new EntityNotFoundException("Question not found"));

                currentQuestion.setContent(uQuestion.getContent());
                currentQuestion.setType(uQuestion.getType());
            } else {
                currentQuestion = questionMapper.toQuestion(uQuestion, test);
                currentQuestion.setTest(test);
                currentQuestion =  questionRepository.save(currentQuestion);
                questionRepository.flush();
            }
            setNewAnswers(currentQuestion, uQuestion.getAnswers());

            questionRepository.save(currentQuestion);
            questionsFromRequest.add(currentQuestion);
        }
        if (test.getQuestions() != null) {
            test.getQuestions().clear();
        }
        test.getQuestions().addAll(questionsFromRequest);

        testRepository.save(test);
        return true;
    }
}
