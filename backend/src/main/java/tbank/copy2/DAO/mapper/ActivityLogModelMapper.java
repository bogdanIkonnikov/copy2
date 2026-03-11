package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.ActivityLog;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.service.model.ActivityLogModel;

@Component
public class ActivityLogModelMapper {
    @Autowired
    private UserRepository userRepository;

    public ActivityLogModel toModel(ActivityLog entity) {
        ActivityLogModel model = new ActivityLogModel();
        model.setId(entity.getId());
        model.setAttemptDate(entity.getAttemptDate());
        model.setUserId(entity.getUser().getId());
        return model;
    }
    public ActivityLog toEntity(ActivityLogModel model) {
        ActivityLog entity = new ActivityLog();
        entity.setAttemptDate(model.getAttemptDate());
        entity.setId(model.getId());
        entity.setUser(userRepository.findById(model.getUserId()).orElse(null));
        return entity;
    }
}
