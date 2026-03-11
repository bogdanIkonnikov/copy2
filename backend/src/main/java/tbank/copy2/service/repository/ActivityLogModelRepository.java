package tbank.copy2.service.repository;

import tbank.copy2.service.model.ActivityLogModel;

public interface ActivityLogModelRepository {
    Long countByUserIdAtLastWeek(Long userId);
    Long countByUserIdAtLastMonth(Long userId);
    Long countByUserIdAtAllTime(Long userId);
    void save(ActivityLogModel activityLogModel);

    ActivityLogModel findByUserId(Long userId);
}
