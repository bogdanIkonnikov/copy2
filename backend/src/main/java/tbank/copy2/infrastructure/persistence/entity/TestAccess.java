package tbank.copy2.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import tbank.copy2.common.enums.AccessLevel;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "test_accesses")
public class TestAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccessLevel accessLevel = AccessLevel.READ;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime sharedAt;
}
