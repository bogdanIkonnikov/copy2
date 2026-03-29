package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.NotificationSettingsModel;
import tbank.copy2.infrastructure.persistence.entity.NotificationSettings;
import tbank.copy2.infrastructure.persistence.repository.TestRepository;
import tbank.copy2.infrastructure.persistence.repository.UserRepository;

@Component
public class NotificationSettingsModelMapper {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;

    public NotificationSettingsModel toModel(NotificationSettings entity) {
        NotificationSettingsModel model = new NotificationSettingsModel();
        model.setId(entity.getId());
        model.setType(entity.getType());
        model.setTestName(testRepository.findById(entity.getTest().getId()).get().getName());
        model.setTestId(entity.getTest().getId());
        model.setUserId(entity.getUser().getId());
        return model;
    }

    public NotificationSettings toEntity(NotificationSettingsModel model) {
        NotificationSettings entity = new NotificationSettings();
        entity.setId(model.getId());
        entity.setType(model.getType());
        entity.setUser(userRepository.findById(model.getUserId()).orElse(null));
        entity.setTest(testRepository.findById(model.getTestId()).orElse(null));
        return entity;
    }
}
