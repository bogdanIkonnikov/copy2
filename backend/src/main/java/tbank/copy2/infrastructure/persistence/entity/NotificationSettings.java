package tbank.copy2.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.common.enums.NotificationType;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "notification_settings")
public class NotificationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private NotificationType type;

    @OneToMany(mappedBy = "settings", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Notification> notifications;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
