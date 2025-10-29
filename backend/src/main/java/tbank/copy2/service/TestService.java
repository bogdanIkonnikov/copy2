package tbank.copy2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.dto.test.*;
import tbank.copy2.entity.Answer;
import tbank.copy2.entity.Question;
import tbank.copy2.entity.Test;
import tbank.copy2.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public List<TestResponse> getTests() {
        List<Test> tests = testRepository.findAll();
        List<TestResponse> testResponses = new ArrayList<>();
        for (Test test : tests) {
            testResponses.add(new TestResponse(test));
        }
        return testResponses;
    }

    public TestResponse addTest(AddTestRequest request) {
        Test test = new Test();
        test.setName(request.getName());
        test.setDescription(request.getDescription());
        Test savedTest = testRepository.save(test);
        return new TestResponse(savedTest);
    }

    public FullTestResponse getTestById(Long id) {
        Test test = testRepository.findById(id).orElse(null);
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : test.getQuestions()) {
            QuestionResponse response = new QuestionResponse();
            response.setId(question.getId());
            response.setContent(question.getContent());
            response.setType(question.getType());
            for (Answer answer : question.getAnswers()) {
                AnswerResponse answerResponse = new AnswerResponse();
                answerResponse.setId(answer.getId());
                answerResponse.setContent(answer.getContent());
                answerResponse.setIsCorrect(answer.getIsCorrect());
                response.getAnswers().add(answerResponse);
            }
            questionResponses.add(response);
        }
        FullTestResponse fullTestResponse = new FullTestResponse();
        fullTestResponse.setId(test.getId());
        fullTestResponse.setQuestions(questionResponses);
        fullTestResponse.setDescription(test.getDescription());
        fullTestResponse.setName(test.getName());
        fullTestResponse.setProgress(14);
        fullTestResponse.setLastUse("10.10.2025");
        return fullTestResponse;
    }

}
