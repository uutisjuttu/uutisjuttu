package fi.uutisjuttu.uutisjuttu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import fi.uutisjuttu.uutisjuttu.profiles.DevProfile;
import fi.uutisjuttu.uutisjuttu.profiles.ProdProfile;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import({DevProfile.class, ProdProfile.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
