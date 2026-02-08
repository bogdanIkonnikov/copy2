package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.Test;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.service.model.TestModel;

import java.util.stream.Collectors;

@Component
public class TestModelMapper {
    @Autowired
    QuestionModelMapper qMapper;
    @Autowired
    private UserRepository userRepository;

    public Test toEntity(TestModel testModel) {
        Test test = new Test();
        test.setId(testModel.getId());
        test.setName(testModel.getName());
        test.setVisible(testModel.getVisible());
        test.setCreated_at(testModel.getCreated_at());
        test.setUpdated_at(testModel.getUpdated_at());
        test.setDescription(testModel.getDescription());
        test.setQuestions(testModel.getQuestions().stream().map(q -> qMapper.toEntity(q)).collect(Collectors.toList()));
        test.setUser(userRepository.findById(testModel.getUserId()).orElse(null));
        return test;
    }

    public TestModel toModel(Test test) {
        TestModel model = new TestModel();
        model.setId(test.getId());
        model.setName(test.getName());
        model.setVisible(test.getVisible());
        model.setCreated_at(test.getCreated_at());
        model.setUpdated_at(test.getUpdated_at());
        model.setDescription(test.getDescription());
        model.setUserId(test.getUser().getId());
        model.setQuestions(test.getQuestions().stream().map(q -> qMapper.toModel(q)).collect(Collectors.toList()));
        return model;
    }

    public Page<TestModel> toPageModel(Page<Test> tests) {
        return tests.map(t -> toModel(t));
    }
}
