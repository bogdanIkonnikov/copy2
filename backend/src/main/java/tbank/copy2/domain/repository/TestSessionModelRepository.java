package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.TestSessionModel;

public interface TestSessionModelRepository {
    TestSessionModel save(TestSessionModel model);
    TestSessionModel getTestSessionById(Long sessionId);
    TestSessionModel getTestSessionByTestIdAndUserId(Long id, Long userId);
    void deleteById(Long id);
}
