package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbank.copy2.infrastructure.persistence.entity.TestSession;

import java.util.Optional;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    @Query("SELECT ts FROM TestSession ts " +
            "WHERE ts.test.id = :testId AND ts.user.id = :userId " +
            "ORDER BY ts.finished_at DESC LIMIT 1")
    Optional<TestSession> findLatestSession(@Param("testId") Long testId, @Param("userId") Long userId);
}
