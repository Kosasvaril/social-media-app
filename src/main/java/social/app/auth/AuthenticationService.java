package social.app.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import social.app.config.JwtService;
import social.app.domain.User;
import org.springframework.stereotype.Service;
import social.app.data.UserRepository;
import social.app.storage.S3Bucket;

import java.io.IOException;


@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private S3Bucket s3Bucket;

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        System.out.println(user);

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public User register(RegisterRequest request) throws IOException {
        User user = new User(request, passwordEncoder.encode(request.getPassword()));
        //userRepository.save(user);
        //var jwtToken = jwtService.generateToken(user);
        //new AuthenticationResponse(jwtToken)
        user = userRepository.save(user);
        s3Bucket.updateUsers();
        return user;

    }
}
