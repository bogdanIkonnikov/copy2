package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbank.copy2.repository.repository.NotificationSettingsModelRepo;
import tbank.copy2.service.mapper.NotificationSettingsServiceMapper;
import tbank.copy2.service.model.NotificationModel;
import tbank.copy2.service.model.NotificationSettingsModel;
import tbank.copy2.service.repository.NotificationModelRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationModelRepository repository;
    @Autowired
    private NotificationSettingsModelRepo settingsRepository;
    @Autowired
    private NotificationSettingsServiceMapper mapper;

    public NotificationModel addNotification(Long userId, Long testId){
        NotificationModel model = new NotificationModel();
        model.setUserId(userId);
        model.setCurrentStep(0);
        model.setLast_sent_at(LocalDateTime.now());
        model.setNext_sent_at(LocalDateTime.now());
        model.setIsEnabled(true);
        model.setTestId(testId);
        return repository.save(model);
    }

    public void deleteNotification(Long userId, Long testId){
        repository.deleteByUserIdAndTestId(userId, testId);
    }

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

    public List<NotificationModel> getActiveNotifications(Long userId) {
        return repository.findEnabledByUserId(userId);
    }
}
