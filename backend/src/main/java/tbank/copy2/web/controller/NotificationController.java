package tbank.copy2.web.controller;

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

    @PostMapping("/{testId}/enable")
    public ResponseEntity<?> enableNotifications(@RequestParam Long testId,
                                                 @AuthenticationPrincipal CurrentUser user) {
        service.enableNotifications(user.getUserId(), testId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{testId}/disable")
    public ResponseEntity<?> disableNotifications(@RequestParam Long testId,
                                                 @AuthenticationPrincipal CurrentUser user) {
        service.disableNotifications(user.getUserId(), testId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public NotificationSettingsResponse setIntervals(@AuthenticationPrincipal CurrentUser user,
                                                     @RequestBody List<Integer> intervals) {
        NotificationSettingsModel model = service.setNotificationSettings(mapper.toModel(user.getUserId(), intervals));
        return mapper.toResponse(model);
    }

    @GetMapping("")
    public NotificationSettingsResponse getIntervals(@AuthenticationPrincipal CurrentUser user) {
        NotificationSettingsModel model = service.getNotificationSettings(user.getUserId());
        return mapper.toResponse(model);
    }
}
