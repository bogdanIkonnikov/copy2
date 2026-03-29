package tbank.copy2.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test_sessions")
public class TestSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            mappedBy = "session",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE}
    )
    private List<UserAnswer> answers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id")
    private Test test;

    @Column(nullable = false, name = "correct_count")
    private long correctCount;

    @Column(nullable = false, name = "total_count")
    private long totalCount;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime started_at;

    @UpdateTimestamp
    @Column()
    private LocalDateTime finished_at;

    @Override
    public String toString() {
        return "TestSession{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : null) +
                ", testId=" + (test != null ? test.getId() : null) +
                ", correctCount=" + correctCount +
                ", totalCount=" + totalCount +
                ", startedAt=" + started_at +
                ", finishedAt=" + finished_at +
                '}';
    }
}