package com.example.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Login;
import com.example.model.Registers;

@Repository
public interface AuthRepos extends JpaRepository<Login,Long>{

    Optional<Login> findByUserName(String userName);

    boolean existsByUserName(String userName);
}