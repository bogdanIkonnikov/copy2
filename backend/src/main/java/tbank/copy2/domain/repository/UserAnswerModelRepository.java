package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.UserAnswerModel;

import java.util.List;

public interface UserAnswerModelRepository {
    List<UserAnswerModel> findUserAnswerModelsBySessionId(Long id);
    UserAnswerModel save(UserAnswerModel userAnswerModel);
}
