package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.TestSessionModelMapper;
import tbank.copy2.domain.repository.TestSessionModelRepository;
import tbank.copy2.infrastructure.persistence.entity.TestSession;
import tbank.copy2.infrastructure.persistence.repository.TestSessionRepository;
import tbank.copy2.domain.model.TestSessionModel;

@Repository
public class TestSessionModelRepoImpl implements TestSessionModelRepository {
    @Autowired
    private TestSessionRepository repository;
    @Autowired
    private TestSessionModelMapper mapper;

    @Override
    public TestSessionModel save(TestSessionModel model) {
        TestSession session = mapper.toEntity(model);
        return mapper.toModel(repository.save(session));
    }

    @Override
    public TestSessionModel getTestSessionById(Long sessionId) {
        TestSession session = repository.findById(sessionId).orElse(null);
        return mapper.toModel(session);
    }

    @Override
    public TestSessionModel getTestSessionByTestIdAndUserId(Long id, Long userId) {
        TestSession session = repository.findLatestSession(id, userId).orElse(null);
        return mapper.toModel(session);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
