package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.UserStatisticModel;

public interface UserStatisticModelRepository {
    void save(UserStatisticModel userStatisticModel);
    UserStatisticModel findById(Long id);
}
