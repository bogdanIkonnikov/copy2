package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbank.copy2.service.service.UserService;
import tbank.copy2.web.dto.user.CurrentUser;
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

    @GetMapping("/me/profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal CurrentUser user) {
        return mapper.toProfileResponse(service.getUserById(user.getUserId()),
                service.getActivityLogs(user.getUserId()),
                service.getRecentActivityLogs(user.getUserId()),
                service.getUniqueTestsCount(user.getUserId()),
                service.getUserStatistic(user.getUserId()));
    }

}
