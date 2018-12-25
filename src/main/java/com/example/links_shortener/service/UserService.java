package com.example.links_shortener.service;

import com.example.links_shortener.dto.UserDto;
import com.example.links_shortener.model.User;
import com.example.links_shortener.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
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

}
