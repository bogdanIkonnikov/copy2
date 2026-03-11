package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.common.enums.Role;
import tbank.copy2.service.model.SignInCommand;
import tbank.copy2.service.model.UserModel;
import tbank.copy2.service.model.UserStatisticModel;
import tbank.copy2.web.dto.user.*;

@Component
public class UserMapper {
    public UserModel toModel(SignUpRequest signUpRequest) {
        UserModel userModel = new UserModel();
        userModel.setUsername(signUpRequest.getUsername());
        userModel.setEmail(signUpRequest.getEmail());
        userModel.setPassword_hash(signUpRequest.getPassword());
        userModel.setRole(Role.USER);
        return userModel;
    }

    public SignInCommand toCommand(SignInRequest request) {
        SignInCommand signInCommand = new SignInCommand();
        signInCommand.setEmail(request.getEmail());
        signInCommand.setPassword(request.getPassword());
        return signInCommand;
    }

    public JwtAuthenticationResponse toAuthenticationResponse(String token) {
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(token);
        return jwtAuthenticationResponse;
    }

    public UserInfoResponse toInfoResponse(UserModel model){
        UserInfoResponse response = new UserInfoResponse();
        response.setEmail(model.getEmail());
        response.setPassword(model.getPassword());
        response.setUsername(model.getUsername());
        response.setRole(model.getRole());
        response.setPhotoURL(model.getPhotoUrl());
        return response;
    }

    public UserStatisticResponse toStatisticResponse(UserStatisticModel model, Long testsCount){
        UserStatisticResponse response = new UserStatisticResponse();
        response.setLongestStreak(model.getLongestStreak());
        response.setCurrentStreak(model.getCurrentStreak());
        response.setTestCount(testsCount);
        response.setCorrectAnswersPercentage((int) (model.getCorrectAnswers() / model.getTotalAnswers()*100));
        return response;
    }

}
