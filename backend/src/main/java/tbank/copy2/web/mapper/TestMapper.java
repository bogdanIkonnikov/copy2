package tbank.copy2.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.web.dto.test.AddTestRequest;
import tbank.copy2.web.dto.test.TestResponse;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.repository.repository.UserRepository;

@Component
public class TestMapper {
    @Autowired
    UserRepository userRepository;

    public TestResponse toTestResponse(Test test) {
        TestResponse testResponse = new TestResponse();
        testResponse.setId(test.getId());
        testResponse.setName(test.getName());
        testResponse.setDescription(test.getDescription());
        testResponse.setProgress(14); //заменить логикой
        testResponse.setLastUse("10.10.2025"); //заменить логикой
        testResponse.setQuestionsCount(test.getQuestions().size());
        return testResponse;
    }

    public Test toEntity(AddTestRequest request) {
        Test test = new Test();
        test.setName(request.getName());
        test.setDescription(request.getDescription());
        test.setUser(userRepository.getById(request.getUserId()));
        return test;
    }
}
