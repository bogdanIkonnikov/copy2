package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.common.enums.Role;
import tbank.copy2.domain.model.*;
import tbank.copy2.web.dto.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        response.setId(model.getId());
        response.setEmail(model.getEmail());
        response.setUsername(model.getUsername());
        response.setPhotoURL(model.getPhotoUrl());
        return response;
    }

    public UserStatisticResponse toStatisticResponse(UserStatisticModel model, int testsCount){
        UserStatisticResponse response = new UserStatisticResponse();
        if (model == null){
            return response;
        }
        response.setLongestStreak(model.getLongestStreak());
        response.setCurrentStreak(model.getCurrentStreak());
        response.setTestsCompleted(testsCount);
        response.setAvgScore((int) (model.getCorrectAnswers() / model.getTotalAnswers()*100));
        return response;
    }

    public UserActivityResponse toActivityResponse(ActivityLogsTransferModel model){
        UserActivityResponse response = new UserActivityResponse();
        if (model == null){
            return response;
        }
        response.setCount(model.getCount());
        response.setDate(model.getDate());
        return response;
    }

    public List<UserAttemptResponse> toAttemptResponses(List<ActivityLogModel> models){
        List<UserAttemptResponse> responses = new ArrayList<>();
        for (ActivityLogModel model : models) {
            UserAttemptResponse response = new UserAttemptResponse();
            response.setDate(model.getAttemptDate());
            response.setTotal(model.getTotal());
            response.setScore(model.getScore());
            response.setTestId(model.getTestId());
            response.setTestName(model.getTestName());
            responses.add(response);
        }
        return responses;
    }

    public UserProfileResponse toProfileResponse(UserModel user,
                                                 List<ActivityLogsTransferModel> transferLogsModels,
                                                 List<ActivityLogModel> activityLogModels,
                                                 int uniqueTestsCount,
                                                 UserStatisticModel userStatistic) {
        UserProfileResponse response = new UserProfileResponse();
        response.setUser(toInfoResponse(user));
        response.setActivity(transferLogsModels.stream().map(a -> toActivityResponse(a)).collect(Collectors.toList()));
        response.setRecentAttempts(toAttemptResponses(activityLogModels));
        response.setStats(toStatisticResponse(userStatistic, uniqueTestsCount));
        return response;
    }
}
