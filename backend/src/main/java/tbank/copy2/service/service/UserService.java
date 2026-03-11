package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.common.enums.Interval;
import tbank.copy2.exception.UserAlreadyExistsException;
import tbank.copy2.service.model.ActivityLogModel;
import tbank.copy2.service.model.UserModel;
import tbank.copy2.service.model.UserStatisticModel;
import tbank.copy2.service.repository.ActivityLogModelRepository;
import tbank.copy2.service.repository.UserModelRepository;
import tbank.copy2.service.repository.UserStatisticModelRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserModelRepository repository;
    @Autowired
    private UserStatisticModelRepository statsRepository;
    @Autowired
    private ActivityLogModelRepository activityRepository;

    public UserDetails getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public UserDetails createUser(UserModel model) {
        if (repository.existsByEmail(model.getEmail())) {
            throw new UserAlreadyExistsException("Пользователь с таким Email уже существует!");
        }
        return repository.save(model);
    }

    public UserModel getUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    public UserStatisticModel getUserStatistic(Long userId) {
        return statsRepository.findById(userId);
    }

    public Long getTestCount(Long userId, Interval interval) {
        switch (interval) {
            case WEEK -> {
                return activityRepository.countByUserIdAtLastWeek(userId);
            }
            case MONTH -> {
                return activityRepository.countByUserIdAtLastMonth(userId);
            }
            case ALL_TIME -> {
                return activityRepository.countByUserIdAtAllTime(userId);
            }
        }
        return 0L;
    }

    @Transactional
    public void addAnswer(boolean isCorrect, Long userId) {
        UserStatisticModel model = statsRepository.findById(userId);
        if (model != null) {
            model.setNew(false);
            model.setTotalAnswers(model.getTotalAnswers() + 1);
            if (isCorrect) {
                model.setCorrectAnswers(model.getCorrectAnswers() + 1);
            }
        }
        else{
            model = new UserStatisticModel();
            model.setNew(true);
            model.setUserId(userId);
            model.setLongestStreak(0);
            model.setTotalAnswers(1L);
            model.setId(userId);
            model.setCurrentStreak(0);
            model.setLastTestDate(null);
            if (isCorrect) {
                model.setCorrectAnswers(1L);
            }
        }
        statsRepository.save(model);
    }

    @Transactional
    public void addActivity(Long userId){
        ActivityLogModel activityModel = new ActivityLogModel();
        activityModel.setUserId(userId);
        activityModel.setAttemptDate(LocalDateTime.now());
        activityRepository.save(activityModel);

        UserStatisticModel statisticModel = statsRepository.findById(userId);
        if (statisticModel != null) {
            statisticModel.setNew(false);
            if (!statisticModel.getLastTestDate().equals(LocalDate.now())){
                statisticModel.setLastTestDate(LocalDate.now());
                statisticModel.setCurrentStreak(statisticModel.getCurrentStreak() + 1);
                if (statisticModel.getCurrentStreak() > statisticModel.getLongestStreak()){
                    statisticModel.setLongestStreak(statisticModel.getCurrentStreak());
                }
            }
        }
        statsRepository.save(statisticModel);
    }
}