package tbank.copy2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.dto.test.TestResponse;
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
            testResponses.add(new TestResponse(test.getName()));
        }
        return testResponses;
    }
}
