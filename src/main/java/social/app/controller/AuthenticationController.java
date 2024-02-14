package social.app.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import social.app.auth.AuthenticationResponse;
import social.app.auth.AuthenticationService;
import social.app.auth.LoginRequest;
import social.app.auth.RegisterRequest;
import social.app.domain.User;
import social.app.storage.S3Bucket;

import java.io.IOException;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController extends HttpServlet {

    @Autowired
    private final AuthenticationService authenticationService;

    @Autowired
    private S3Bucket s3Bucket;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request, HttpServletResponse response) throws IOException {
        try {
            AuthenticationResponse authResp = authenticationService.login(request);
            Cookie cookie = createCookie(authResp);
            response.addCookie(cookie);
            response.getWriter().write("Login was successful!");
        }catch (BadCredentialsException e){
            response.getWriter().write("Your password or email is incorrect!");
        }
    }

    private Cookie createCookie(AuthenticationResponse authResp) {
        Cookie cookie = new Cookie("jwtToken", authResp.token());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(1000 * 60 * 60 * 24);
        cookie.setAttribute("SameSite", "None");
        return cookie;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody RegisterRequest request
    ){
            User user = authenticationService.register(request);
            return ResponseEntity.ok(user);
    }
}
