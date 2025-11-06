package tbank.copy2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.dto.test.*;
import tbank.copy2.entity.Test;
import tbank.copy2.repository.TestRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    public List<TestResponse> getTests() {
        List<Test> tests = testRepository.findAll();
        List<TestResponse> testResponses= new ArrayList<>();
        for (Test test : tests) {
            TestResponse testResponse = new TestResponse();
            testResponse.setId(test.getId());
            testResponse.setName(test.getName());
            testResponse.setDescription(test.getDescription());
            testResponse.setProgress(14); //заменить логикой
            testResponse.setLastUse("10.10.2025");
            testResponse.setQuestionsCount(test.getQuestions().size());
            testResponses.add(testResponse);
        }
        return testResponses;
    }

    public TestResponse addTest(AddTestRequest request) {
        Test test = new Test();
        test.setName(request.getName());
        test.setDescription(request.getDescription());
        Test savedTest = testRepository.save(test);
        TestResponse testResponse = new TestResponse();
        testResponse.setId(savedTest.getId());
        testResponse.setDescription(test.getDescription());
        testResponse.setName(test.getName());
        testResponse.setProgress(0);
        testResponse.setLastUse(null);
        return testResponse;
    }

    public TestResponse getTestById(Long id) {
        Test test = testRepository.findById(id).orElse(null);
        TestResponse testResponse = new TestResponse();
        testResponse.setId(test.getId());
        testResponse.setDescription(test.getDescription());
        testResponse.setName(test.getName());
        testResponse.setProgress(14);
        testResponse.setLastUse("10.10.2025");
        testResponse.setQuestionsCount(test.getQuestions().size());
        return testResponse;
    }
}
