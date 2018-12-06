package com.example.linksShortener.repository;

import com.example.linksShortener.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>{

    User findByUserName(String name);
}