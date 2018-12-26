package com.example.links_shortener.core.service;

import com.example.links_shortener.core.dto.UserDto;

import com.example.links_shortener.core.model.User;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto);
    User findByEmail(String email);
    User save(User user);
}
