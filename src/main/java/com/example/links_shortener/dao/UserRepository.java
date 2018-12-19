package com.example.links_shortener.dao;

import com.example.links_shortener.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}