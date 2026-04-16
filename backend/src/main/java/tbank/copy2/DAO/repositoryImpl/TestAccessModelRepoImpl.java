package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.TestAccessModelMapper;
import tbank.copy2.domain.model.TestAccessModel;
import tbank.copy2.domain.repository.TestAccessModelRepository;
import tbank.copy2.infrastructure.persistence.repository.TestAccessRepository;

@Repository
public class TestAccessModelRepoImpl implements TestAccessModelRepository {
    @Autowired
    private TestAccessRepository repository;
    @Autowired
    private TestAccessModelMapper mapper;

    @Override
    public void save(TestAccessModel model) {
        repository.save(mapper.toEntity(model));
    }

    @Override
    public void delete(TestAccessModel model) {
        repository.delete(mapper.toEntity(model));
    }
}
