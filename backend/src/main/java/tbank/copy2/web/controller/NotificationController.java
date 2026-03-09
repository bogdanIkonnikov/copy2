package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.service.model.NotificationSettingsModel;
import tbank.copy2.service.service.NotificationService;
import tbank.copy2.web.dto.notification.NotificationSettingsResponse;
import tbank.copy2.web.dto.user.CurrentUser;
import tbank.copy2.web.mapper.NotificationMapper;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService service;
    @Autowired
    private NotificationMapper mapper;

    @Operation(description = "Включить уведомления у пользователя для конкретного теста")
    @PostMapping("/{testId}/enable")
    public ResponseEntity<?> enableNotifications(@Parameter(description = "Идентификатор теста", example = "1")
                                                 @RequestParam Long testId,
                                                 @AuthenticationPrincipal CurrentUser user) {
        service.enableNotifications(user.getUserId(), testId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Выключить уведомления у пользователя для конкретного теста")
    @PostMapping("/{testId}/disable")
    public ResponseEntity<?> disableNotifications(@Parameter(description = "Идентификатор теста", example = "1")
                                                  @RequestParam Long testId,
                                                  @AuthenticationPrincipal CurrentUser user) {
        service.disableNotifications(user.getUserId(), testId);
        return ResponseEntity.ok().build();
    }

    @Operation(description = "Выставить интервалы уведомлений пользователя")
    @PutMapping("")
    public NotificationSettingsResponse setIntervals(@AuthenticationPrincipal CurrentUser user,
                                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                             description = "Список интервалов",
                                                             required = true,
                                                             content = @Content(mediaType = "application/json")
                                                     )
                                                     @RequestBody List<Integer> intervals) {
        NotificationSettingsModel model = service.setNotificationSettings(mapper.toModel(user.getUserId(), intervals));
        return mapper.toResponse(model);
    }

    @Operation(description = "Получить интервалы уведомлений пользователя")
    @GetMapping("")
    public NotificationSettingsResponse getIntervals(@AuthenticationPrincipal CurrentUser user) {
        NotificationSettingsModel model = service.getNotificationSettings(user.getUserId());
        return mapper.toResponse(model);
    }
}
