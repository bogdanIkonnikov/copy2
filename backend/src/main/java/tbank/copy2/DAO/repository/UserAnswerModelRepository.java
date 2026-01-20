package tbank.copy2.DAO.repository;

import tbank.copy2.service.model.UserAnswerModel;

import java.util.List;

public interface UserAnswerModelRepository {
    List<UserAnswerModel> findUserAnswerModelsBySessionId(Long id);
    UserAnswerModel save(UserAnswerModel userAnswerModel);
}
