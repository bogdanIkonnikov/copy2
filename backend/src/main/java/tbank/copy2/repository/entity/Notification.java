package tbank.copy2.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Test test;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private Boolean isEnabled = true;

    @Column()
    private LocalDateTime last_sent_at;

    @Column(nullable = false)
    private LocalDateTime next_sent_at;

    @Column(nullable = false)
    private int interval;
}
