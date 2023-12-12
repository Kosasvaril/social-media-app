package social.app;


import com.github.cliftonlabs.json_simple.JsonException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import social.app.storage.S3Bucket;

import java.io.IOException;

@SpringBootApplication(scanBasePackages={
        "social.app", "social.app.application"})
public class SocialMediaApp {
    public static void main(String[] args) throws JsonException, IOException {
        SpringApplication.run(SocialMediaApp.class, args);
    }
}
