package tbank.copy2.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckedAnswerModel {
    private List<Object> rightAnswers;
    private boolean isTrue;
}