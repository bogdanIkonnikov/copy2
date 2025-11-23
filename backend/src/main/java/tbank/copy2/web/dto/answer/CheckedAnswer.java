package tbank.copy2.web.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckedAnswer {
    private List<Object> rightAnswers;
    private boolean isTrue;
}
