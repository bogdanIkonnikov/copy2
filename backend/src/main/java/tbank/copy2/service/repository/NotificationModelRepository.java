package tbank.copy2.service.repository;

import tbank.copy2.service.model.NotificationModel;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationModelRepository {
    List<NotificationModel> findAllForRemind(LocalDateTime now);

    void save(NotificationModel n);
}
