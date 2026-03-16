package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.service.service.UserService;
import tbank.copy2.web.dto.user.ChangePasswordRequest;
import tbank.copy2.web.dto.user.CurrentUser;
import tbank.copy2.web.dto.user.EditUsernameEmailRequest;
import tbank.copy2.web.dto.user.UserProfileResponse;
import tbank.copy2.web.mapper.UserMapper;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователь", description = "Операции, связанные с пользователем")
public class UserController {
    @Autowired
    private UserMapper mapper;

    private final UserService service;

    public UserController(@Qualifier("userService") UserService service) {
        this.service = service;
    }

    @Operation(description = "Получить профиль текущего пользователя")
    @GetMapping("/me/profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal CurrentUser user) {
        return mapper.toProfileResponse(service.getUserById(user.getUserId()),
                service.getActivityLogs(user.getUserId()),
                service.getRecentActivityLogs(user.getUserId()),
                service.getUniqueTestsCount(user.getUserId()),
                service.getUserStatistic(user.getUserId()));
    }

    @Operation(description = "Изменить имя пользователя и email текущего пользователя")
    @PatchMapping("/me")
    public ResponseEntity<?> editProfile(@RequestBody EditUsernameEmailRequest request, @AuthenticationPrincipal CurrentUser user) {
        service.updateUserEmailAndUsername(user.getUserId(), request.getEmail(), request.getUsername());
        return ResponseEntity.ok("Данные успешно изменены");
    }

    @Operation(description = "Изменить пароль текущего пользователя")
    @PostMapping("/me/change-password")
    public ResponseEntity<?> changePassword(@AuthenticationPrincipal CurrentUser user, @RequestBody ChangePasswordRequest request) {
        service.changePassword(user.getUserId(), request.getCurrentPassword(), request.getNewPassword());
        return ResponseEntity.ok("Пароль успешно изменён");
    }
}
