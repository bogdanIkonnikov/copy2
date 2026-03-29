package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.QuestionModel;

import java.util.List;

public interface QuestionModelRepository {
    QuestionModel save(QuestionModel questionModel);
    List<QuestionModel> findAllByTestId(Long testId);
    QuestionModel findById(Long id);
    void flush();
}
