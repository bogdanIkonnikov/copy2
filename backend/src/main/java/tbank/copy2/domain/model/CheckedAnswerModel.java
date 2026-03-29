package tbank.copy2.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckedAnswerModel {
    private List<Object> rightAnswers;
    private boolean isTrue;
}