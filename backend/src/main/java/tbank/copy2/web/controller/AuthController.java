package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbank.copy2.service.service.AuthenticationService;
import tbank.copy2.web.dto.question.AddQuestionRequest;
import tbank.copy2.web.dto.user.JwtAuthenticationResponse;
import tbank.copy2.web.dto.user.SignInRequest;
import tbank.copy2.web.dto.user.SignUpRequest;
import tbank.copy2.web.mapper.UserMapper;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Операции, связанные с аутентификацией")
public class AuthController {
    @Autowired
    private AuthenticationService service;
    @Autowired
    private UserMapper mapper;

    @Operation(summary = "Регистрация нового пользователя")
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для регистрации нового пользователя",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignUpRequest.class))
            )
            @RequestBody @Valid SignUpRequest request) {
        String token = service.signUp(mapper.toModel(request));
        return mapper.toAuthenticationResponse(token);
    }

    @Operation(summary = "Вход пользователя")
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для входа пользователя",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SignInRequest.class))
            )
            @RequestBody @Valid SignInRequest request) {
        String token = service.signIn(mapper.toCommand(request));
        return mapper.toAuthenticationResponse(token);
    }
}