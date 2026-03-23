package com.example.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Buy;

public interface Buyrepo extends JpaRepository<Buy, Integer> {

    // 🔥 THIS IS MAIN FIX
    @Query("SELECT b FROM Buy b JOIN FETCH b.upload WHERE b.upload.ownerName = :ownerName")
    List<Buy> findMessagesWithRoom(@Param("ownerName") String ownerName);

}