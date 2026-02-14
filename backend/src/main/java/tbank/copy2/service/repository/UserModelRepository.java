package tbank.copy2.service.repository;

import tbank.copy2.service.model.UserModel;

public interface UserModelRepository {
    UserModel findByUsername(String username);
    UserModel save(UserModel userModel);
}
