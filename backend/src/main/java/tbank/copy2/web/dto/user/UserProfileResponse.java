package tbank.copy2.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserProfileResponse {
    UserInfoResponse user;
    UserStatisticResponse stats;
    List<UserActivityResponse> activity;
    List<UserAttemptResponse> recentAttempts;
}
