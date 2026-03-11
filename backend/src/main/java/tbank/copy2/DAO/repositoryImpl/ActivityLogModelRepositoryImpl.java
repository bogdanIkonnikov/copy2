package tbank.copy2.DAO.repositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tbank.copy2.DAO.mapper.ActivityLogModelMapper;
import tbank.copy2.repository.repository.ActivityLogRepository;
import tbank.copy2.service.model.ActivityLogModel;
import tbank.copy2.service.repository.ActivityLogModelRepository;

import java.time.LocalDateTime;

@Repository
public class ActivityLogModelRepositoryImpl implements ActivityLogModelRepository {
    @Autowired
    private ActivityLogRepository repository;
    @Autowired
    private ActivityLogModelMapper mapper;

    @Override
    public Long countByUserIdAtLastWeek(Long userId) {
        return repository.countByUserIdAndAttemptDateAfter(userId, LocalDateTime.now().minusWeeks(1));
    }

    @Override
    public Long countByUserIdAtLastMonth(Long userId) {
        return repository.countByUserIdAndAttemptDateAfter(userId, LocalDateTime.now().minusMonths(1));
    }

    @Override
    public Long countByUserIdAtAllTime(Long userId) {
        return repository.countByUserId(userId);
    }

    @Override
    public void save(ActivityLogModel activityLogModel) {
        repository.save(mapper.toEntity(activityLogModel));
    }
}
