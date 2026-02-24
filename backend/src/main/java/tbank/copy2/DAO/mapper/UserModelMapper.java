package tbank.copy2.DAO.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.User;
import tbank.copy2.service.model.UserModel;

@Component
public class UserModelMapper {
    public UserModel toModel(User user) {
        UserModel model = new UserModel();
        model.setId(user.getId());
        model.setUsername(user.getUsername());
        model.setEmail(user.getEmail());
        model.setRole(user.getRole());
        model.setPhotoUrl(user.getPhotoUrl());
        model.setPassword_hash(user.getPassword_hash());
        model.setCreated_at(user.getCreated_at());
        model.setUpdated_at(user.getUpdated_at());
        return model;
    }
    public User toEntity(UserModel userModel) {
        User user = new User();
        user.setId(userModel.getId());
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setRole(userModel.getRole());
        user.setPhotoUrl(userModel.getPhotoUrl());
        user.setPassword_hash(userModel.getPassword_hash());
        user.setCreated_at(userModel.getCreated_at());
        user.setUpdated_at(userModel.getUpdated_at());
        return user;
    }
}
