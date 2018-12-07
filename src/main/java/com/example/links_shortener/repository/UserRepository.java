package com.example.links_shortener.repository;

import com.example.links_shortener.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{

    User findByUserName(String name);
}