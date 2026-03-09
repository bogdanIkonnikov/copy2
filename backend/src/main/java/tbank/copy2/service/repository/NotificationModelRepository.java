package tbank.copy2.service.repository;

import tbank.copy2.service.model.NotificationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationModelRepository {
    List<NotificationModel> findAllForRemind(LocalDateTime now);

    void save(NotificationModel n);

    NotificationModel findByUserIdAndTestId(Long userId, Long testId);
}
