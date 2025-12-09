package tbank.copy2.DAO.repository;

import tbank.copy2.service.model.TestModel;

import java.util.List;

public interface TestModelRepository {
    List<TestModel> findAll();

    TestModel save(TestModel model);

    TestModel findById(Long id);
}
