package com.example.links_shortener.dao;

import com.example.links_shortener.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{

    User findByEmail(String email);
}