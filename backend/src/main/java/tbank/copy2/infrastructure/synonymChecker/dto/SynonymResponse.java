package tbank.copy2.infrastructure.synonymChecker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SynonymResponse(@JsonProperty("correct") boolean correct, @JsonProperty("similarity") double similarity) {
    public boolean isCorrect(){
        return correct;
    }
}
