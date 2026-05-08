package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.TestAccessModel;

public interface TestAccessModelRepository {
    void save(TestAccessModel model);
    void delete(TestAccessModel model);
}
