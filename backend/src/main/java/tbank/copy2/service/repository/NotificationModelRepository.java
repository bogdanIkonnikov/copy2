package tbank.copy2.service.repository;

import tbank.copy2.service.model.NotificationModel;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationModelRepository {
    List<NotificationModel> findAllForRemind(LocalDateTime now);

    NotificationModel save(NotificationModel n);

    NotificationModel findByUserIdAndTestId(Long userId, Long testId);

    List<NotificationModel> findEnabledByUserId(Long userId);

    void deleteByUserIdAndTestId(Long userId, Long testId);
}
