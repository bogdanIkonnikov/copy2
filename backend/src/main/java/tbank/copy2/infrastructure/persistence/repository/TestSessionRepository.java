package tbank.copy2.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.infrastructure.persistence.entity.TestSession;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    TestSession getTestSessionById(Long sessionId);
    TestSession findByTestIdAndUserId(Long testId, Long userId);
}
