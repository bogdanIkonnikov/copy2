package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.TestAccessModel;
import tbank.copy2.infrastructure.persistence.entity.TestAccess;
import tbank.copy2.infrastructure.persistence.repository.TestRepository;
import tbank.copy2.infrastructure.persistence.repository.UserRepository;

@Component
public class TestAccessModelMapper {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;

    public TestAccess toEntity(TestAccessModel model){
        TestAccess entity = new TestAccess();
        entity.setTest(testRepository.findById(model.getTestId()).orElse(null));
        entity.setId(model.getId());
        entity.setUser(userRepository.findById(model.getUserId()).orElse(null));
        entity.setAccessLevel(model.getAccessLevel());
        entity.setSharedAt(model.getSharedAt());
        return entity;
    }

    public TestAccessModel toModel(TestAccess entity){
        TestAccessModel model = new TestAccessModel();
        model.setAccessLevel(entity.getAccessLevel());
        model.setSharedAt(entity.getSharedAt());
        model.setTestId(entity.getTest().getId());
        model.setUserId(entity.getUser().getId());
        model.setId(entity.getId());
        return model;
    }
}
