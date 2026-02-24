package tbank.copy2.service.repository;

import tbank.copy2.service.model.UserModel;

public interface UserModelRepository {
    UserModel findByEmail(String email);
    UserModel save(UserModel userModel);
    UserModel findById(Long id);
}
