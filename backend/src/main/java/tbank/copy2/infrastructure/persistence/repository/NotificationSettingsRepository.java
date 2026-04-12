package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tbank.copy2.infrastructure.persistence.entity.NotificationSettings;
import tbank.copy2.infrastructure.persistence.entity.User;

import java.util.List;

@Repository
public interface NotificationSettingsRepository extends JpaRepository<NotificationSettings, Long> {
    List<NotificationSettings> findAllByUserId(Long userId);
    boolean existsByUserIdAndTestId(Long userId, Long testId);

    List<NotificationSettings> user(User user);

    void deleteByUserIdAndTestId(Long userId, Long testId);
    @Query("""
        SELECT DISTINCT n.settings.id
        FROM Notification n
        WHERE n.settings.id NOT IN (
            SELECT DISTINCT n2.settings.id
            FROM Notification n2
            WHERE n2.isSent = false
        )
    """)
    List<Long> findSettingsIdsWhereAllSent();
}
