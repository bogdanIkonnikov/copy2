package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.infrastructure.persistence.entity.ActivityLog;
import tbank.copy2.infrastructure.persistence.repository.TestRepository;
import tbank.copy2.infrastructure.persistence.repository.UserRepository;
import tbank.copy2.domain.model.ActivityLogModel;

@Component
public class ActivityLogModelMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRepository testRepository;

    public ActivityLogModel toModel(ActivityLog entity) {
        ActivityLogModel model = new ActivityLogModel();
        model.setId(entity.getId());
        model.setAttemptDate(entity.getAttemptDate());
        model.setScore(entity.getScore());
        model.setTestId(entity.getTest().getId());
        model.setTestName(entity.getTest().getName());
        model.setUserId(entity.getUser().getId());
        return model;
    }
    public ActivityLog toEntity(ActivityLogModel model) {
        ActivityLog entity = new ActivityLog();
        entity.setAttemptDate(model.getAttemptDate());
        entity.setId(model.getId());
        entity.setScore(model.getScore());
        entity.setTotal(model.getScore());
        entity.setTest(testRepository.getById(model.getTestId()));
        entity.setUser(userRepository.findById(model.getUserId()).orElse(null));
        return entity;
    }
}
