package tbank.copy2.repository.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_statistics")
public class UserStatistic implements Persistable<Long> {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private boolean isNew = true;

    @Column(name = "total_answers")
    private Long totalAnswers;

    @Column(name = "correct_answers")
    private Long correctAnswers;

    @Column(name = "current_streak")
    private int currentStreak;

    @Column(name = "longest_streak")
    private int longestStreak;

    @Column(name = "last_test_date")
    private LocalDateTime lastTestDate;
}
