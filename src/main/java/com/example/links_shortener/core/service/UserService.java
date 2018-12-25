package com.example.links_shortener.core.service;

import com.example.links_shortener.core.dto.UserDto;
import com.example.links_shortener.core.model.User;
import com.example.links_shortener.core.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    public User registerNewUserAccount(UserDto accountDto) {

        if (emailExist(accountDto.getEmail())) {
            return null;
        }

        User user = new User();
        user.setUsername(accountDto.getUsername());
        user.setEnabled(true);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String  encodedPassword = encoder.encode(accountDto.getPassword());

        user.setPassword(encodedPassword);
        user.setEmail(accountDto.getEmail());

        return repository.save(user);

    }
    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

}
