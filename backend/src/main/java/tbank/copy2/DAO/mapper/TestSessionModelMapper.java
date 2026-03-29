// java
package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.infrastructure.persistence.entity.TestSession;
import tbank.copy2.infrastructure.persistence.repository.TestRepository;
import tbank.copy2.infrastructure.persistence.repository.UserRepository;
import tbank.copy2.domain.model.TestSessionModel;

@Component
public class TestSessionModelMapper {
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;

    public TestSession toEntity(TestSessionModel model) {
        if (model == null) return null;
        TestSession testSession = new TestSession();
        testSession.setId(model.getId());
        testSession.setUser(userRepository.findById(model.getUserId()).orElse(null));
        testSession.setTest(testRepository.findById(model.getTestId()).orElse(null));
        testSession.setCorrectCount(model.getCorrectCount());
        testSession.setFinished_at(model.getFinished_at());
        testSession.setTotalCount(model.getTotalCount());
        testSession.setStarted_at(model.getStarted_at());
        return testSession;
    }

    public TestSessionModel toModel(TestSession session) {
        if (session == null) return null;
        TestSessionModel model = new TestSessionModel();
        model.setId(session.getId());
        if (session.getTest() != null) {
            model.setTestId(session.getTest().getId());
            model.setTestName(session.getTest().getName());
        }
        if (session.getUser() != null) {
            model.setUserId(session.getUser().getId());
        }
        model.setCorrectCount(session.getCorrectCount());
        model.setFinished_at(session.getFinished_at());
        model.setTotalCount(session.getTotalCount());
        model.setStarted_at(session.getStarted_at());
        return model;
    }
}