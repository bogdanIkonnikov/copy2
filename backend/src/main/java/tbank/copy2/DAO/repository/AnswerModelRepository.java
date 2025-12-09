package tbank.copy2.DAO.repository;


import tbank.copy2.service.model.AnswerModel;

import java.util.List;

public interface AnswerModelRepository {
    List<AnswerModel> findAllByQuestionId(Long id);
    AnswerModel save(AnswerModel answerModel);
}
