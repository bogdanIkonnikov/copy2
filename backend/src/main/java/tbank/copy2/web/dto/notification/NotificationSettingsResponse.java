package tbank.copy2.web.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationSettingsResponse {
    private Long userId;
    private int[] intervals;
}
