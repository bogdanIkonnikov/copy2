package tbank.copy2.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tbank.copy2.domain.model.SignInCommand;
import tbank.copy2.domain.model.UserModel;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtService.TokenPair signUp(UserModel user) {
        user.setPassword_hash(passwordEncoder.encode(user.getPassword()));
        userService.createUser(user);

        return jwtService.generateTokenPair(user);
    }

    public JwtService.TokenPair signIn(SignInCommand command) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                command.getEmail(),
                command.getPassword()
        ));

        var user = userService.getByEmail(command.getEmail());

        return jwtService.generateTokenPair(user);
    }
}