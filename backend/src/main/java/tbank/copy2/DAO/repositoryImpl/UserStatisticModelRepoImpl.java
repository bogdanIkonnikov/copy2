package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.UserStatisticModelMapper;
import tbank.copy2.infrastructure.persistence.repository.UserStatisticRepository;
import tbank.copy2.domain.model.UserStatisticModel;
import tbank.copy2.domain.repository.UserStatisticModelRepository;

@Repository
public class UserStatisticModelRepoImpl implements UserStatisticModelRepository {
    @Autowired
    private UserStatisticRepository repository;
    @Autowired
    private UserStatisticModelMapper mapper;

    @Override
    public void save(UserStatisticModel userStatisticModel) {
        repository.save(mapper.toEntity(userStatisticModel));
    }

    @Override
    public UserStatisticModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }
}
