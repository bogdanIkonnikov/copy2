package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.UserModel;

public interface UserModelRepository {
    UserModel findByEmail(String email);
    UserModel save(UserModel userModel);
    UserModel findById(Long id);

    boolean existsByEmail(String email);
}
