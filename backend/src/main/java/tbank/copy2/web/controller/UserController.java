package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.domain.service.JwtService;
import tbank.copy2.domain.service.UserService;
import tbank.copy2.web.dto.user.*;
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
    public JwtAuthenticationResponse editProfile(@RequestBody EditUsernameEmailRequest request, @AuthenticationPrincipal CurrentUser user) {
        JwtService.TokenPair pair = service.updateUserEmailAndUsername(user.getUserId(), request.getEmail(), request.getUsername());
        return mapper.toAuthenticationResponse(pair);
    }

    @Operation(description = "Изменить пароль текущего пользователя")
    @PostMapping("/me/change-password")
    public JwtAuthenticationResponse changePassword(@AuthenticationPrincipal CurrentUser user, @RequestBody ChangePasswordRequest request) {
        JwtService.TokenPair pair = service.changePassword(user.getUserId(), request.getCurrentPassword(), request.getNewPassword());
        return mapper.toAuthenticationResponse(pair);
    }
}
