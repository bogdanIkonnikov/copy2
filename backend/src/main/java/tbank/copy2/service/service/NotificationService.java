package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.repository.repository.NotificationSettingsModelRepo;
import tbank.copy2.service.mapper.NotificationSettingsMapper;
import tbank.copy2.service.model.NotificationModel;
import tbank.copy2.service.model.NotificationSettingsModel;
import tbank.copy2.service.repository.NotificationModelRepository;

@Service
public class NotificationService {
    @Autowired
    private NotificationModelRepository repository;
    @Autowired
    private NotificationSettingsModelRepo settingsRepository;
    @Autowired
    private NotificationSettingsMapper mapper;


    public void enableNotifications(Long userId, Long testId) {
        NotificationModel model = repository.findByUserIdAndTestId(userId, testId);
        model.setIsEnabled(true);
        repository.save(model);
    }

    public void disableNotifications(Long userId, Long testId) {
        NotificationModel model = repository.findByUserIdAndTestId(userId, testId);
        model.setIsEnabled(false);
        repository.save(model);
    }

    public NotificationSettingsModel setNotificationSettings(NotificationSettingsModel model) {
        NotificationSettingsModel settings;
        if (settingsRepository.existsByUserId(model.getUserId())) {
            settings = settingsRepository.findByUserId(model.getUserId());
        } else{
            settings = new NotificationSettingsModel();
            settings.setUserId(model.getUserId());
        }
        mapper.setIntervals(settings, model);
        settingsRepository.save(settings);
        return settings;
    }

    public NotificationSettingsModel getNotificationSettings(Long userId) {
        return settingsRepository.findByUserId(userId);
    }
}
