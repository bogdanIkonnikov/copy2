package tbank.copy2.web.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAttemptResponse {
    private Long testId;
    private String testName;
    private int score;
    private int total;
    private String date;
}
