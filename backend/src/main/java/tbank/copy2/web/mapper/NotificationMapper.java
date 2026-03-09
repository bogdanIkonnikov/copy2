package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.service.model.NotificationSettingsModel;
import tbank.copy2.web.dto.notification.NotificationSettingsResponse;

import java.util.List;

@Component
public class NotificationMapper {
    public NotificationSettingsModel toModel(Long userId, List<Integer> intervals) {
        NotificationSettingsModel model = new NotificationSettingsModel();
        model.setUserId(userId);
        model.setStep0(intervals.get(0));
        model.setStep1(intervals.get(1));
        model.setStep2(intervals.get(2));
        model.setStep3(intervals.get(3));
        model.setStep4(intervals.get(4));
        model.setStep5(intervals.get(5));
        return model;
    }

    public NotificationSettingsResponse toResponse(NotificationSettingsModel model) {
        NotificationSettingsResponse response = new NotificationSettingsResponse();
        response.setUserId(model.getUserId());
        int[] intervals = new int[6];
        intervals[0] = model.getStep0();
        intervals[1] = model.getStep1();
        intervals[2] = model.getStep2();
        intervals[3] = model.getStep3();
        intervals[4] = model.getStep4();
        intervals[5] = model.getStep5();
        response.setIntervals(intervals);
        return response;
    }
}
