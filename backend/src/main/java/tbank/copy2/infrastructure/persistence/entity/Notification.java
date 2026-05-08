package tbank.copy2.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.NotificationStatus;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_settings_id")
    private NotificationSettings settings;

    @Column(nullable = false, name = "version")
    private int version = 0;

    @Column(nullable = false, name = "sent_at")
    private LocalDateTime sent_at;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private NotificationStatus status = NotificationStatus.PENDING;

    @Column(name = "error_message")
    private String errorMessage;
}
