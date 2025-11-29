package tbank.copy2.service.service;

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
import tbank.copy2.web.mapper.AnswerMapper;
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
    private AnswerMapper answerMapper;
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
    private void setNewAnswers(Question question, List<UpdateAnswerRequest> answers){
        List<Answer> answerList = new ArrayList<>();
        for (UpdateAnswerRequest uAnswer : answers) {
            Answer answer = answerMapper.toEntity(uAnswer, question);
            if (uAnswer.getAnswerId() != null) {
                answer.setId(uAnswer.getAnswerId());
            }
            answerRepository.save(answer);
            answerList.add(answer);
        }
        question.setAnswers(answerList);
    }

    @Transactional
    public boolean updateTest(UpdateTestRequest request, Long testId) {
        Test test = testRepository.findById(testId).orElse(null);
        List<Question> newQuestions = new ArrayList<>();
        for (UpdateQuestionRequest uQuestion : request.getQuestions()) {
            Question newQuestion = questionMapper.toQuestion(uQuestion, test);
            if (uQuestion.getQuestionId() != null){
                newQuestion.setId(uQuestion.getQuestionId());
            }
            setNewAnswers(newQuestion, uQuestion.getAnswers());
            questionRepository.save(newQuestion);
            newQuestions.add(newQuestion);
        }
        test.setQuestions(newQuestions);
        testRepository.save(test);
        return true;
    }
}
