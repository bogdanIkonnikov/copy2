package tbank.copy2.domain.repository;

import tbank.copy2.domain.model.ActivityLogModel;

import java.util.List;

public interface ActivityLogModelRepository {
    Long countByUserIdAtLastWeek(Long userId);
    Long countByUserIdAtLastMonth(Long userId);
    Long countByUserIdAtAllTime(Long userId);
    void save(ActivityLogModel activityLogModel);
    List<ActivityLogModel> findAllByUserId(Long userId);
    List<ActivityLogModel> findTop5ByUserIdOrderByAttemptDateDesc(Long userId);
}
