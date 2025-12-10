package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.TestSession;
import tbank.copy2.repository.repository.TestRepository;
import tbank.copy2.service.model.TestSessionModel;

@Component
public class TestSessionModelMapper {
    @Autowired
    private TestRepository testRepository;

    public TestSession toEntity(TestSessionModel model) {
        TestSession testSession = new TestSession();
        testSession.setId(model.getId());
        testSession.setTest(testRepository.findById(model.getTestId()).orElse(null));
        testSession.setCorrectCount(model.getCorrectCount());
        testSession.setFinished_at(model.getFinished_at());
        testSession.setTotalCount(model.getTotalCount());
        testSession.setStarted_at(model.getStarted_at());
        return testSession;
    }

    public TestSessionModel toModel(TestSession session) {
        TestSessionModel model = new TestSessionModel();
        model.setId(session.getId());
        model.setTestId(session.getTest().getId());
        model.setCorrectCount(session.getCorrectCount());
        model.setFinished_at(session.getFinished_at());
        model.setTotalCount(session.getTotalCount());
        model.setStarted_at(session.getStarted_at());
        model.setTestName(session.getTest().getName());
        model.setUserId(session.getUser().getId());
        return null;
    }
}
