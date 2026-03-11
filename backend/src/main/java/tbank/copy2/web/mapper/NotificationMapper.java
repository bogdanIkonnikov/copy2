package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.service.model.NotificationModel;
import tbank.copy2.web.dto.notification.NotificationResponse;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationMapper {
    public NotificationResponse toResponse(NotificationModel model) {
        NotificationResponse response = new NotificationResponse();
        response.setId(model.getId());
        response.setCurrentStep(model.getCurrentStep());
        response.setNextSentAt(model.getNext_sent_at().toString());
        response.setEnabled(model.getIsEnabled());
        response.setTestName(model.getTestName());
        return response;
    }

    public List<NotificationResponse> toResponses(List<NotificationModel> models) {
        return models.stream().map(m -> toResponse(m)).collect(Collectors.toList());
    }
}
