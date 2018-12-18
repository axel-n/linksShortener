package com.example.links_shortener;

import com.example.links_shortener.dao.UserRepository;
import com.example.links_shortener.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    public void demoData() {

        User user = new User();
        user.setUsername("alex");
        user.setEnabled(true);

        // Create an encoder with strength 11
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
		String  encodedPassword = encoder.encode("123456");

        user.setPassword(encodedPassword);
        user.setEmail("alex@gmail.com");

        userRepository.save(user);
    }
}



