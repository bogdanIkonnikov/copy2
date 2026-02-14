package tbank.copy2.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbank.copy2.service.service.AuthenticationService;
import tbank.copy2.web.dto.user.JwtAuthenticationResponse;
import tbank.copy2.web.dto.user.SignInRequest;
import tbank.copy2.web.dto.user.SignUpRequest;
import tbank.copy2.web.mapper.UserMapper;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthenticationService service;
    @Autowired
    private UserMapper mapper;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        String token = service.signUp(mapper.toModel(request));
        return mapper.toAuthenticationResponse(token);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        String token = service.signIn(mapper.toCommand(request));
        return mapper.toAuthenticationResponse(token);
    }
}