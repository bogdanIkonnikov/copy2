package tbank.copy2.domain.repository;


import tbank.copy2.domain.model.AnswerModel;

import java.util.List;

public interface AnswerModelRepository {
    List<AnswerModel> findAllByQuestionId(Long id);
    AnswerModel save(AnswerModel answerModel);
    void flush();
    AnswerModel findById(Long id);
}
