package tbank.copy2.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tbank.copy2.common.enums.Interval;
import tbank.copy2.exception.EmailAlreadyTakenException;
import tbank.copy2.exception.UserAlreadyExistsException;
import tbank.copy2.service.model.ActivityLogModel;
import tbank.copy2.service.model.ActivityLogsTransferModel;
import tbank.copy2.service.model.UserModel;
import tbank.copy2.service.model.UserStatisticModel;
import tbank.copy2.service.repository.ActivityLogModelRepository;
import tbank.copy2.service.repository.TestModelRepository;
import tbank.copy2.service.repository.UserModelRepository;
import tbank.copy2.service.repository.UserStatisticModelRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserModelRepository repository;
    @Autowired
    private UserStatisticModelRepository statsRepository;
    @Autowired
    private ActivityLogModelRepository activityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TestModelRepository testRepository;

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
                model.setCorrectAnswers(((model.getCorrectAnswers() == null) ? 0 : model.getCorrectAnswers())  + 1);
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
            model.setLastTestDate(LocalDate.now());
            if (isCorrect) {
                model.setCorrectAnswers(1L);
                model.setCorrectAnswers(0L);
            }
        }
        statsRepository.save(model);
    }

    @Transactional
    public void addActivity(Long userId, Long testId, String testName, int total, int score) {
        if (testRepository.findById(testId).getVisible()) {
            ActivityLogModel activityModel = new ActivityLogModel();
            activityModel.setUserId(userId);
            activityModel.setAttemptDate(LocalDateTime.now());
            activityModel.setTestId(testId);
            activityModel.setScore(score);
            activityModel.setTotal(total);
            activityModel.setTestName(testName);
            activityRepository.save(activityModel);

            UserStatisticModel statisticModel = statsRepository.findById(userId);
            if (statisticModel != null) {
                statisticModel.setNew(false);
                if (statisticModel.getLastTestDate() == null || !statisticModel.getLastTestDate().equals(LocalDate.now())){
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

    public List<ActivityLogsTransferModel> getActivityLogs(Long userId) {
        List<ActivityLogModel> activityLogs = activityRepository.findAllByUserId(userId);
        List<ActivityLogsTransferModel> activityLogsTransferModels = new ArrayList<>();
        Map<LocalDate, Integer> logsMap = new HashMap<>();
        for (ActivityLogModel activityLog : activityLogs) {
            LocalDate date = activityLog.getAttemptDate().toLocalDate();
            if (logsMap.containsKey(date)) {
                logsMap.put(date, logsMap.get(date) + 1);
            } else {
                logsMap.put(date, 1);
            }
        }
        for (Map.Entry<LocalDate, Integer> entry : logsMap.entrySet()) {
            activityLogsTransferModels.add(new ActivityLogsTransferModel(entry.getKey(), entry.getValue()));
        }
        return activityLogsTransferModels;
    }

    public List<ActivityLogModel> getRecentActivityLogs(Long userId) {
        List<ActivityLogModel> activityLogs = activityRepository.findTop5ByUserIdOrderByAttemptDateDesc(userId);
        return activityLogs;
    }

    public int getUniqueTestsCount(Long userId) {
        List<ActivityLogModel> activityLogs = activityRepository.findAllByUserId(userId);
        return (int) activityLogs.stream()
                .filter(l -> l.getScore() == l.getTotal())
                .map(ActivityLogModel::getTestId)
                .distinct()
                .count();
    }

    public void updateUserEmailAndUsername(Long userId, String newEmail, String newUsername) {
        UserModel user = repository.findById(userId);
        if (repository.existsByEmail(newEmail)) throw new EmailAlreadyTakenException("Этот email уже занят");
        if (user != null) {
            user.setEmail(newEmail);
            user.setUsername(newUsername);
            repository.save(user);
        }
        else {
            throw new RuntimeException("Пользователь не найден!");
        }
    }

     public void updateUserPhotoURL(Long userId, String newPhotoURL) {
         UserModel user = repository.findById(userId);
         if (user != null) {
             user.setPhotoUrl(newPhotoURL);
             repository.save(user);
         }
     }

    public void changePassword(Long userId, String currentPassword, String newPassword) {
        UserModel user = repository.findById(userId);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Неверный текущий пароль!");
        }
        user.setPassword_hash(passwordEncoder.encode(newPassword));
        repository.save(user);
    }
}