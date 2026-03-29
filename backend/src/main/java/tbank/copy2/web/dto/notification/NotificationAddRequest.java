package tbank.copy2.web.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class NotificationAddRequest {
    private Long testId;
    private NotificationType mode;
    private List<LocalDateTime> reminders;
}
