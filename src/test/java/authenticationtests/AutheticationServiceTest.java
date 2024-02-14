package authenticationtests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import social.app.auth.AuthenticationService;
import social.app.auth.RegisterRequest;
import social.app.config.JwtService;
import social.app.data.UserRepository;
import social.app.domain.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AutheticationServiceTest {

    static UserRepository mockedRepository = Mockito.mock(UserRepository.class);
    static PasswordEncoder mockedEncoder = Mockito.mock(PasswordEncoder.class);
    static JwtService mockedJwtService = Mockito.mock(JwtService.class);
    @Autowired
    AuthenticationManager authenticationManager;
    static AuthenticationManager mockedManager = Mockito.mock(AuthenticationManager.class);
    static AuthenticationService systemUnderTest;

    @BeforeAll
    public static void setup(){
        systemUnderTest = new AuthenticationService(
                mockedRepository, mockedEncoder, mockedJwtService, mockedManager
        );
    }

    @Test
    @DisplayName("Successful registration should return new user")
    void registrationShouldReturnUser(){
        ///Given
        RegisterRequest request = new RegisterRequest("testUser", "testPwd","testName");
        User user = new User(request, "");
        ///When
        when(mockedRepository.save(any(User.class))).thenReturn(user);
        when(mockedEncoder.encode(request.getPassword())).thenReturn("");
        ///Then
        assertEquals(systemUnderTest.register(request), user);
    }

   //@Test
   //@DisplayName("Successful login should return new AuthenticationResponse")
   //void loginShouldReturnAuthenticationResponse(){
   //    LoginRequest loginRequest = new LoginRequest("testUser", "testPwd");
   //    authenticationManager.authenticate(
   //            new UsernamePasswordAuthenticationToken(
   //                    loginRequest.getEmail(),
   //                    loginRequest.getPassword()
   //            )
   //    );
   //    ///Given
   //    User user = new User(
   //            "testUser",
   //            loginRequest.getPassword(),
   //            loginRequest.getEmail(),
   //            new ArrayList<>(), new ArrayList<>(),
   //            Role.USER,
   //            10);
   //    user.setEmail(loginRequest.getEmail());
   //    user.setPassword(loginRequest.getPassword());
   //    JwtService jwtService = new JwtService();

   //    //When
   //    when(mockedRepository.findByEmail(any(String.class))).thenReturn(
   //            Optional.of(user)
   //    );
   //    ///Then
   //    AuthenticationResponse response = new AuthenticationResponse(jwtService.generateToken(user));
   //    assertEquals(systemUnderTest.login(loginRequest), response);
   //}


}
