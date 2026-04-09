package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.domain.model.NotificationModel;
import tbank.copy2.domain.model.NotificationSettingsModel;
import tbank.copy2.domain.repository.NotificationModelRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationModelRepository repository;

    public List<NotificationModel> addNotification(List<LocalDateTime> reminders, NotificationSettingsModel settings) {
        List<NotificationModel> result = new ArrayList<>();
        for (LocalDateTime reminder : reminders) {
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setSent_at(reminder);
            notificationModel.setTestId(settings.getTestId());
            notificationModel.setSent(false);
            notificationModel.setSettings(settings);
            result.add(repository.save(notificationModel));
        }
        return result;
    }

    public List<NotificationModel> getAllNotifications(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
