package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.common.enums.Role;
import tbank.copy2.service.model.SignInCommand;
import tbank.copy2.service.model.UserModel;
import tbank.copy2.web.dto.user.JwtAuthenticationResponse;
import tbank.copy2.web.dto.user.SignInRequest;
import tbank.copy2.web.dto.user.SignUpRequest;

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
}
