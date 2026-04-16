package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.AccessLevel;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TestAccessModel {
    private Long id;
    private Long testId;
    private Long userId;
    private AccessLevel accessLevel = AccessLevel.READ;
    private LocalDateTime sharedAt;
}
