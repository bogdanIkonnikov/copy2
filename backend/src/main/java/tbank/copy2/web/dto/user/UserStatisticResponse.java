package tbank.copy2.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStatisticResponse {
    private int testsCompleted;
    private int avgScore;
    private int currentStreak;
    private int longestStreak;
}
