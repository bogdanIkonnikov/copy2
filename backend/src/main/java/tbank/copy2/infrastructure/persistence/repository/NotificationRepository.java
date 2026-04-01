package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbank.copy2.infrastructure.persistence.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.settings.user.id = :userId")
    List<Notification> findAllByUserId(@Param("userId") Long userId);
    @Query("SELECT n FROM Notification n WHERE n.sent_at < :time AND n.isSent = :sent")
    List<Notification> findAllBySentAndSentAtBefore(
            @Param("time") LocalDateTime time,
            @Param("sent") boolean sent);

}
