package tbank.copy2.infrastructure.persistence.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "answers")
    public class Answer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "question_id")
        private Question question;

        @Column(nullable = false)
        private String content;

        @Column(name = "is_correct", nullable = false)
        private Boolean isCorrect;

        @CreationTimestamp
        @Column(nullable = false)
        private LocalDateTime created_at;

        @UpdateTimestamp
        @Column(nullable = false)
        private LocalDateTime updated_at;

        @Override
        public String toString() {
            return "Answer{" +
                    "id=" + id +
                    ", content='" + content + '\'' +
                    ", isCorrect=" + isCorrect +
                    ", questionId=" + (question != null ? question.getId() : null) +
                    ", createdAt=" + created_at +
                    ", updatedAt=" + updated_at +
                    '}';
        }
    }
