package tbank.copy2.web.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NotificationResponse {
    private Long testId;
    private String testName;
    private String mode;
    private List<NotificationSmallResponse> reminders;
}