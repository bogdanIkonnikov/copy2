package tbank.copy2.web.mapper;

import org.springframework.stereotype.Component;
import tbank.copy2.domain.model.NotificationModel;
import tbank.copy2.domain.model.NotificationSettingsModel;
import tbank.copy2.web.dto.notification.NotificationResponse;
import tbank.copy2.web.dto.notification.NotificationSmallResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationMapper {
    public NotificationResponse toResponse(List<NotificationSmallResponse> responses, NotificationSettingsModel settings) {
        NotificationResponse response = new NotificationResponse();
        response.setTestName(settings.getTestName());
        response.setMode(settings.getType().toString());
        response.setReminders(responses);
        response.setTestId(settings.getTestId());
        return response;
    }

    public NotificationSmallResponse toSmallResponse(NotificationModel model) {
        NotificationSmallResponse response = new NotificationSmallResponse();
        response.setId(model.getId());
        response.setDatetime(model.getSent_at().toString() + "Z");
        return response;
    }

    public List<NotificationResponse> toResponses(List<NotificationModel> models, List<NotificationSettingsModel> settings) {
        List<NotificationResponse> responses = new ArrayList<>();
        List<NotificationSmallResponse> smallResponses;
        for (NotificationSettingsModel setting : settings) {
            Long testId = setting.getTestId();
            smallResponses = models.stream()
                    .filter(model -> (model.getTestId().equals(testId)))
                    .map(this::toSmallResponse)
                    .collect(Collectors.toList());
            NotificationResponse response = toResponse(smallResponses, setting);
            responses.add(response);
        }
        return responses;
    }
}
