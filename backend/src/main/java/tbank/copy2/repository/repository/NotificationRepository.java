package tbank.copy2.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbank.copy2.repository.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.isEnabled = :isEnabled AND n.next_sent_at < :timestamp")
    List<Notification> findAllToRemind(@Param("isEnabled") Boolean isEnabled,
                                       @Param("timestamp") LocalDateTime timestamp);
    Notification findByUserIdAndTestId(Long userId, Long testId);

    List<Notification> findAllEnabledByUserId(Long userId);

    void deleteByUserIdAndTestId(Long userId, Long testId);
}
