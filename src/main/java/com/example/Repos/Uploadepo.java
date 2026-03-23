package com.example.Repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Upload;

public interface Uploadepo extends JpaRepository<Upload,Long>{
	List<Upload> findByOwnerName(String ownerName);
}
