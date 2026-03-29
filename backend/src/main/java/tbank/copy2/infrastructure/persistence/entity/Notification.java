package tbank.copy2.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false, name = "sent_at")
    private LocalDateTime sent_at;

    @Column(nullable = false, name = "is_sent")
    private boolean isSent;
}
