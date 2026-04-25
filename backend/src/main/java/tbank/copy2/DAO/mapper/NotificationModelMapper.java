package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.infrastructure.persistence.entity.Notification;
import tbank.copy2.domain.model.NotificationModel;

@Component
public class NotificationModelMapper {
    @Autowired
    private NotificationSettingsModelMapper settingsModelMapper;

    public Notification toEntity(NotificationModel model) {
        Notification entity = new Notification();
        entity.setId(model.getId());
        entity.setSettings(settingsModelMapper.toEntity(model.getSettings()));
        entity.setSent(model.isSent());
        entity.setVersion(model.getVersion());
        entity.setSent_at(model.getSent_at());
        return entity;
    }

    public NotificationModel toModel(Notification entity) {
        NotificationModel model = new NotificationModel();
        model.setId(entity.getId());
        model.setSent(entity.isSent());
        model.setTestId(entity.getSettings().getTest().getId());
        model.setSent_at(entity.getSent_at());
        model.setVersion(entity.getVersion());
        model.setEmail(entity.getSettings().getUser().getEmail());
        model.setTestName(entity.getSettings().getTest().getName());
        model.setSettings(settingsModelMapper.toModel(entity.getSettings()));
        return model;
    }
}
