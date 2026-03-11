package tbank.copy2.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.repository.entity.ActivityLog;

import java.time.LocalDateTime;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    Long countByUserIdAndAttemptDateAfter(Long userId, LocalDateTime date);
    Long countByUserId(Long userId);

    ActivityLog findByUserId(Long userId);
}
