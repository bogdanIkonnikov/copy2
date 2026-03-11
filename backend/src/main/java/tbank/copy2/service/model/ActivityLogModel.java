package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ActivityLogModel {
    private Long id;
    private Long userId;
    private LocalDateTime attemptDate;
}
