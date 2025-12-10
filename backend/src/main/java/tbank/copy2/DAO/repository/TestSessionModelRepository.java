package tbank.copy2.DAO.repository;

import tbank.copy2.service.model.TestSessionModel;

public interface TestSessionModelRepository {
    TestSessionModel save(TestSessionModel model);
    TestSessionModel getTestSessionById(Long sessionId);
}
