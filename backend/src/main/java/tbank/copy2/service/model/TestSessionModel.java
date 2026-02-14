package tbank.copy2.service.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class TestSessionModel {
    private Long id;

    private Long userId;

    private Long testId;

    private String testName;

    private long correctCount;

    private long totalCount;

    private LocalDateTime started_at;

    private LocalDateTime finished_at;
}
