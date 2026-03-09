package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.Notification;
import tbank.copy2.repository.repository.TestRepository;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.service.model.NotificationModel;

@Component
public class NotificationModelMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestRepository testRepository;

    public Notification toEntity(NotificationModel model) {
        Notification entity = new Notification();
        entity.setId(model.getId());
        entity.setTest(testRepository.findById(model.getTestId()).orElse(null));
        entity.setUser(userRepository.findById(model.getUserId()).orElse(null));
        entity.setIsEnabled(model.getIsEnabled());
        entity.setLast_sent_at(model.getLast_sent_at());
        entity.setNext_sent_at(model.getNext_sent_at());
        entity.setCurrentStep(model.getCurrentStep());
        return entity;
    }

    public NotificationModel toModel(Notification entity) {
        NotificationModel model = new NotificationModel();
        model.setId(entity.getId());
        model.setTestId(entity.getTest().getId());
        model.setTestName(entity.getTest().getName());
        model.setUserId(entity.getUser().getId());
        model.setEmail(entity.getUser().getEmail());
        model.setIsEnabled(entity.getIsEnabled());
        model.setLast_sent_at(entity.getLast_sent_at());
        model.setNext_sent_at(entity.getNext_sent_at());
        model.setCurrentStep(entity.getCurrentStep());
        return model;
    }
}
