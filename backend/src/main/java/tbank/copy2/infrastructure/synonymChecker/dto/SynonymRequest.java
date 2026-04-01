package tbank.copy2.infrastructure.synonymChecker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SynonymRequest(@JsonProperty("correct_answer") String correctAnswer,
                             @JsonProperty("user_answer") String userAnswer,
                             @JsonProperty("threshold") double threshold) {}
