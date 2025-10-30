package tbank.copy2.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tbank.copy2.enums.Type;

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

        @Column
        private String content;

        @Column(name = "is_correct")
        private Boolean isCorrect;

        @Column
        private LocalDateTime created_at;

        @Column
        private LocalDateTime updated_at;
    }
