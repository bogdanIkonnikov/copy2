package tbank.copy2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbank.copy2.entity.TestSession;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSession, Long> {
    TestSession getTestSessionById(Long sessionId);
}
