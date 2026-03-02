package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.UserModelMapper;
import tbank.copy2.repository.entity.User;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.service.model.UserModel;
import tbank.copy2.service.repository.UserModelRepository;

@Repository
public class UserModelRepoImpl implements UserModelRepository {
    @Autowired
    private UserModelMapper mapper;
    @Autowired
    private UserRepository repository;


    @Override
    public UserModel findByEmail(String email) {
        return mapper.toModel(repository.findByEmail(email).orElse(null));
    }

    @Override
    public UserModel save(UserModel userModel) {
        User user = repository.save(mapper.toEntity(userModel));
        return mapper.toModel(user);
    }

    @Override
    public UserModel findById(Long id) {
        return mapper.toModel(repository.findById(id).orElse(null));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
