package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.web.mapper.TestMapper;
import tbank.copy2.repository.repository.TestRepository;
import tbank.copy2.web.dto.test.AddTestRequest;
import tbank.copy2.web.dto.test.TestResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TestMapper testMapper;

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
}
