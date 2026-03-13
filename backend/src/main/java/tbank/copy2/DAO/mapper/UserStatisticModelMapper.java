package tbank.copy2.DAO.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbank.copy2.repository.entity.UserStatistic;
import tbank.copy2.repository.repository.UserRepository;
import tbank.copy2.service.model.UserStatisticModel;

@Component
public class UserStatisticModelMapper {
    @Autowired
    private UserRepository userRepository;

     public UserStatisticModel toModel(UserStatistic entity) {
         if (entity == null) {
             return null;
         }
         UserStatisticModel model = new UserStatisticModel();
         model.setId(entity.getId());
         model.setUserId(entity.getUser().getId());
         model.setCurrentStreak(entity.getCurrentStreak());
         model.setCorrectAnswers(entity.getCorrectAnswers());
         model.setLongestStreak(entity.getLongestStreak());
         model.setTotalAnswers(entity.getTotalAnswers());
         model.setNew(entity.isNew());
         model.setLastTestDate(entity.getLastTestDate());
         return model;
     }
     public UserStatistic toEntity(UserStatisticModel model) {
         UserStatistic entity = new UserStatistic();
         entity.setUser(userRepository.findById(model.getUserId()).orElse(null));
         entity.setCurrentStreak(model.getCurrentStreak());
         entity.setLongestStreak(model.getLongestStreak());
         entity.setTotalAnswers(model.getTotalAnswers());
         entity.setCorrectAnswers(model.getCorrectAnswers());
         entity.setLastTestDate(model.getLastTestDate());
         entity.setNew(model.isNew());
         entity.setId(model.getId());
         return entity;
     }
}
