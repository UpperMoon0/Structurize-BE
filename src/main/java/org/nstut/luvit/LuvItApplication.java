package org.nstut.luvit;

import org.nstut.luvit.entity.User;
import org.nstut.luvit.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class LuvItApplication {
    public static void main(String[] args) {
        SpringApplication.run(LuvItApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(UserService userService) {
        return args -> {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                System.out.println("No user");
            } else {
                users.forEach(System.out::println);
            }
        };
    }
}
