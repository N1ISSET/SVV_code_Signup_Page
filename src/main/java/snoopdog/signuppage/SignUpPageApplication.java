package snoopdog.signuppage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import snoopdog.signuppage.model.User;


@SpringBootApplication
public class SignUpPageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignUpPageApplication.class, args);
    }
}


