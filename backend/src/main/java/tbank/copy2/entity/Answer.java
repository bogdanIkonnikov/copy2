package tbank.copy2.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

        @Column(nullable = false)
        private LocalDateTime created_at;

        @Column(nullable = false)
        private LocalDateTime updated_at;
    }
