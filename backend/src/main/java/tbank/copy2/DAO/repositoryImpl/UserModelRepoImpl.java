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
    public UserModel findByUsername(String username) {
        return mapper.toModel(repository.findByUsername(username).orElse(null));
    }

    @Override
    public UserModel save(UserModel userModel) {
        User user = repository.save(mapper.toEntity(userModel));
        return mapper.toModel(user);
    }
}
