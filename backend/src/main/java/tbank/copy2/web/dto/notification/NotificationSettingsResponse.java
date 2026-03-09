package tbank.copy2.web.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class NotificationSettingsResponse {
    private Long userId;
    private int[] intervals;
}
