package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.infrastructure.persistence.entity.ActivityLog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    Long countByUserIdAndAttemptDateAfter(Long userId, LocalDateTime date);
    Long countByUserId(Long userId);

    ActivityLog findByUserId(Long userId);

    List<ActivityLog> findAllByUserId(Long userId);
    List<ActivityLog> findTop5ByUserIdOrderByAttemptDateDesc(Long userId);

}
