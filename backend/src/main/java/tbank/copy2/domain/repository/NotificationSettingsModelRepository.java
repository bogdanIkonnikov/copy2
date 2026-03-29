package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.NotificationSettingsModel;

import java.util.List;

public interface NotificationSettingsModelRepository {
    List<NotificationSettingsModel> getAllNotificationSettings(Long userId);
    NotificationSettingsModel save(NotificationSettingsModel notificationSettingsModel);

    boolean existsByUserIdAndTestId(Long userId, Long testId);

    void deleteByUserIdAndTestId(Long userId, Long testId);
}
