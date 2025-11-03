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

    public List<Long> getTestsId() {
        List<Test> tests = testRepository.findAll();
        List<Long> testsId= new ArrayList<>();
        for (Test test : tests) {
            testsId.add(test.getId());
        }
        return testsId;
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
        return testResponse;
    }
}
