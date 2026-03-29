package tbank.copy2.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.common.enums.NotificationType;
import tbank.copy2.domain.model.NotificationSettingsModel;
import tbank.copy2.domain.repository.NotificationSettingsModelRepository;
import tbank.copy2.domain.repository.TestModelRepository;
import tbank.copy2.exception.RemindersAlreadyExistsException;
import tbank.copy2.exception.RemindersNotFoundException;

import java.util.List;

@Service
public class NotificationSettingsService {
    @Autowired
    private NotificationSettingsModelRepository repository;
    @Autowired
    private TestModelRepository testRepository;

    public NotificationSettingsModel addNotification(Long userId, Long testId, NotificationType notificationType) {
        if (repository.existsByUserIdAndTestId(userId, testId)) throw new RemindersAlreadyExistsException("Уведомление для данного теста уже существует!");
        NotificationSettingsModel model = new NotificationSettingsModel();
        model.setTestId(testId);
        model.setUserId(userId);
        model.setType(notificationType);
        model.setTestName(testRepository.findById(testId).getName());
        return repository.save(model);
    }

    public List<NotificationSettingsModel> getAllNotificationSettings(Long userId) {
        return repository.getAllNotificationSettings(userId);
    }

    @Transactional
    public void deleteSettings(Long userId, Long testId) {
        if (!repository.existsByUserIdAndTestId(userId, testId))
            throw new RemindersNotFoundException("Напоминания для пользователя с userId =" + userId + " и testId =" + testId + " не найдены");
        repository.deleteByUserIdAndTestId(userId, testId);
    }
}
