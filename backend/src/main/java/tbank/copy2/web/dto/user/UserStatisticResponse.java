package tbank.copy2.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStatisticResponse {
    private Long testCount;
    private int correctAnswersPercentage;
    private int currentStreak;
    private int longestStreak;
}
