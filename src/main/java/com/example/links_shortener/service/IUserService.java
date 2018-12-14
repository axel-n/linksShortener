package com.example.links_shortener.service;

import com.example.links_shortener.dto.UserDto;
import com.example.links_shortener.error.EmailExistsException;
import com.example.links_shortener.model.User;

public interface IUserService {
    User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException;
}
