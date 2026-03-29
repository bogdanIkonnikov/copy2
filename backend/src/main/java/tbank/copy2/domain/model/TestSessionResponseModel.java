package tbank.copy2.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class TestSessionResponseModel {
    private Boolean saved;

    private Boolean isCorrect;

    private List<Object> correctAnswer;
}
