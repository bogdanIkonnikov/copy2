package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.common.enums.NotificationStatus;
import tbank.copy2.infrastructure.persistence.entity.Notification;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n " +
            "LEFT JOIN FETCH n.settings s " +
            "LEFT JOIN FETCH s.test " +
            "LEFT JOIN FETCH s.user " +
            "WHERE n.sent_at < :time AND n.status IN ('PENDING', 'ERROR')")
    List<Notification> findAllToProcess(@Param("time") LocalDateTime time);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = :newStatus, n.version = n.version + 1 " +
            "WHERE n.id = :id AND n.version = :version")
    int updateStatus(@Param("id") Long id, @Param("version") int version, @Param("newStatus") NotificationStatus newStatus);

    @Modifying
    @Transactional
    @Query("UPDATE Notification n SET n.status = :status, n.errorMessage = :err " +
            "WHERE n.id = :id")
    void updateStatusFinal(@Param("id") Long id, @Param("status") NotificationStatus status, @Param("err") String err);
}
