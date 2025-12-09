package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@NoArgsConstructor
public class TestModel {
    private Long id;

    private String name;

    private Long userId;

    private String description;

    private List<QuestionModel> questions = new ArrayList<>();

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
