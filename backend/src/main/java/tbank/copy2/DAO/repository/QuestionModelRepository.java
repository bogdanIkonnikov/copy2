package tbank.copy2.DAO.repository;

import tbank.copy2.service.model.QuestionModel;

import java.util.List;

public interface QuestionModelRepository {
    QuestionModel save(QuestionModel questionModel);
    List<QuestionModel> findAllByTestId(Long testId);
    QuestionModel findById(Long id);

    void flush();
}
