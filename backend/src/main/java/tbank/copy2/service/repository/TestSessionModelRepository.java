package tbank.copy2.service.repository;

import tbank.copy2.service.model.TestSessionModel;

public interface TestSessionModelRepository {
    TestSessionModel save(TestSessionModel model);
    TestSessionModel getTestSessionById(Long sessionId);
    TestSessionModel getTestSessionByTestIdAndUserId(Long id, Long userId);

    void delete(TestSessionModel oldModel);
}
