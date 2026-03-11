package tbank.copy2.service.repository;

import tbank.copy2.service.model.UserStatisticModel;

public interface UserStatisticModelRepository {
    void save(UserStatisticModel userStatisticModel);
    UserStatisticModel findById(Long id);
}
