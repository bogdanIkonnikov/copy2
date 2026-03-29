package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.NotificationModel;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationModelRepository {
    List<NotificationModel> findAllByUserId(Long userId);

    NotificationModel save(NotificationModel model);

    List<NotificationModel> findAllBySentAtBeforeAndSent(LocalDateTime now, boolean b);

    void saveAll(List<NotificationModel> notifications);
}
