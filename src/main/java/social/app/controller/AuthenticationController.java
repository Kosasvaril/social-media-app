package social.app.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social.app.auth.AuthenticationResponse;
import social.app.auth.AuthenticationService;
import social.app.auth.LoginRequest;
import social.app.auth.RegisterRequest;
import social.app.domain.User;
import social.app.storage.S3Bucket;

import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private S3Bucket s3Bucket;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<Cookie> login(
            @RequestBody LoginRequest request
    ){
        AuthenticationResponse authResp = authenticationService.login(request);
        Cookie cookie = new Cookie("JwtToken", authResp.getToken());
        cookie.setMaxAge(24 * 60 * 60);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        return ResponseEntity.ok(cookie);
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody RegisterRequest request
    ){
        try {
            User user = authenticationService.register(request);
            s3Bucket.updateUsers();
            return ResponseEntity.ok(user);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
