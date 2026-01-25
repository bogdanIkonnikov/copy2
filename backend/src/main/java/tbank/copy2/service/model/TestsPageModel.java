package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@NoArgsConstructor
public class TestsPageModel {
    List<TestModel> models = new ArrayList<>();
    private int totalModels;
    private int totalPages;
}
