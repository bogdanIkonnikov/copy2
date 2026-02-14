package tbank.copy2.service.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class TestsPageModel {
    List<TestModel> models = new ArrayList<>();
    private int totalModels;
    private int totalPages;
}
