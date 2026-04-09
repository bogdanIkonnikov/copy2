package tbank.copy2.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import tbank.copy2.domain.service.AuthenticationService;
import tbank.copy2.domain.service.VerificationService;
import tbank.copy2.web.dto.user.JwtAuthenticationResponse;
import tbank.copy2.web.dto.user.SignInRequest;
import tbank.copy2.web.dto.user.SignUpRequest;
import tbank.copy2.web.dto.verification.VerificationRequest;
import tbank.copy2.web.mapper.UserMapper;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Операции, связанные с аутентификацией")
public class AuthController {
    @Autowired
    private AuthenticationService service;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private VerificationService verificationService;
    @Autowired
    private JavaMailSender mailSender;

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

    @Operation(summary = "Подтверждение почты")
    @PostMapping("/verify")
    public ResponseEntity<?> verify(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для подтверждения почты пользователя",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VerificationRequest.class))
            )
            @RequestBody VerificationRequest request) {
        boolean isVerified = verificationService.verifyCode(request.getEmail(), request.getCode());
        if (isVerified) {
            return ResponseEntity.ok("Почта подтверждена!");
        } else {
            return ResponseEntity.badRequest().body("Неверный код или срок действия истек");
        }
    }

    @Operation(summary = "Отправка кода подтверждения на почту")
    @PostMapping("/send-code")
    public ResponseEntity<?> sendVerificationCode(@Parameter(name = "Почта пользователя") @RequestParam String email) {
        verificationService.sendVerificationCode(email);
        return ResponseEntity.ok("Код подтверждения отправлен на почту");
    }
}