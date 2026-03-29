package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.domain.model.NotificationModel;
import tbank.copy2.domain.model.NotificationSettingsModel;
import tbank.copy2.domain.service.NotificationService;
import tbank.copy2.domain.service.NotificationSettingsService;
import tbank.copy2.web.dto.notification.NotificationAddRequest;
import tbank.copy2.web.dto.notification.NotificationResponse;
import tbank.copy2.web.dto.notification.NotificationSmallResponse;
import tbank.copy2.web.dto.user.CurrentUser;
import tbank.copy2.web.mapper.NotificationMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reminders")
public class NotificationController {
    @Autowired
    private NotificationService service;
    @Autowired
    private NotificationSettingsService settingsService;
    @Autowired
    private NotificationMapper mapper;

    @Operation(description = "Получить список всех напоминаний пользователя")
    @GetMapping()
    public List<NotificationResponse> getAllNotifications(@AuthenticationPrincipal CurrentUser user) {
        List<NotificationModel> notifications = service.getAllNotifications(user.getUserId());
        List<NotificationSettingsModel> settings = settingsService.getAllNotificationSettings(user.getUserId());
        return mapper.toResponses(notifications, settings);
    }

    @Operation(description = "Добавить напоминание")
    @PostMapping()
    public NotificationResponse addNotification(@AuthenticationPrincipal CurrentUser user, @RequestBody NotificationAddRequest request) {
        NotificationSettingsModel settingsModel = settingsService.addNotification(user.getUserId(), request.getTestId(), request.getMode());
        List<NotificationSmallResponse> responses = service.addNotification(request.getReminders(), settingsModel)
                .stream().map(mapper::toSmallResponse).collect(Collectors.toList());
        return mapper.toResponse(responses, settingsModel);
    }

    @DeleteMapping("/{testId}")
    public ResponseEntity<?> deleteNotifications(@AuthenticationPrincipal CurrentUser user, @PathVariable Long testId) {
        settingsService.deleteSettings(user.getUserId(), testId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{testId}")
    public NotificationResponse editNotifications(@AuthenticationPrincipal CurrentUser user,
                                                  @PathVariable Long testId,
                                                  @RequestBody NotificationAddRequest request){
        settingsService.deleteSettings(user.getUserId(), testId);
        NotificationSettingsModel settingsModel = settingsService.addNotification(user.getUserId(), testId, request.getMode());
        List<NotificationSmallResponse> responses = service.addNotification(request.getReminders(), settingsModel)
                .stream().map(mapper::toSmallResponse).collect(Collectors.toList());
        return mapper.toResponse(responses, settingsModel);
    }
}
