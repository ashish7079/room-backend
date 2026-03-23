package com.example.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Repos.Buyrepo;
import com.example.Repos.Uploadepo;
import com.example.model.Buy;
import com.example.model.Upload;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class UploadController {

	@Autowired
	Uploadepo repo;
	
	@Autowired
	Buyrepo rep;
	
//	@PostMapping("/Roomowner/dashboard")
//	@PreAuthorize("hasRole('RoomOwner')")
	@PostMapping("/Roomowner/dashboard")
	public String userDashboard(
	    @RequestParam("file") List<MultipartFile> files,
	    @RequestParam Integer price,
	    @RequestParam Long contact,
	    @RequestParam String address,
	    @RequestParam String facility,
	    @RequestParam String description
	) {
	    try {

	        String fileNames = "";

	        for (MultipartFile file : files) {
	            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
	            Path path = Paths.get("uploads/" + fileName);
	            Files.createDirectories(path.getParent());
	            Files.write(path, file.getBytes());

	            fileNames += fileName + ",";
	        }

	        Upload doc = new Upload();
	        doc.setUpload(fileNames);
	        doc.setPrice(price);
	        doc.setContact(contact);
	        doc.setAddress(address);
	        doc.setFacility(facility);
	        doc.setDescription(description);

	        doc.setOwnerName(
	            SecurityContextHolder.getContext().getAuthentication().getName()
	        );

	        repo.save(doc);

	        return "Uploaded";

	    } catch (Exception e) {
	        return "Error";
	    }
	}
	
	@GetMapping("/RoomOwner/show")
//	@PreAuthorize("hasRole('RoomOwner')")
	public List<Upload> get(){
		return repo.findAll();
	}
	
	@PutMapping("/RoomOwner/update/{id}")
	@PreAuthorize("hasRole('RoomOwner')")
	public String updates(@PathVariable Long id,
			@RequestParam("file") MultipartFile file,
			@RequestParam("price") Integer price,
			@RequestParam("address") String address,
			@RequestParam("facility") String facility,
			@RequestParam("description") String description
			) {
		
		Upload dc = repo.findById(id).orElseThrow();
		
		dc.setUpload(file.getOriginalFilename());
		dc.setPrice(price);
		dc.setAddress(address);
		dc.setFacility(facility);
		dc.setDescription(description);
		
		repo.save(dc);
		
		return "updated successfully...";
	}
	
	@DeleteMapping("/RoomOwner/delete/{id}")
	@PreAuthorize("hasRole('RoomOwner')")
	public String deleted(@PathVariable Long id) {
		
		Upload ds = repo.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
		
		repo.delete(ds);
		return "deleted Successfully...";
	}
	
	@GetMapping("/provider/dashboard")
	@PreAuthorize("hasRole('PROVIDER')")
	public String providerDashboard(){
	    return "Provider Dashboard";
	}
	
	@PostMapping("/user/buynow/{id}")
	@PreAuthorize("hasRole('USER')")
	public String buy(@RequestParam String userName,
					 @RequestParam String description,
					 @PathVariable Long id
			) {
		 
		Upload upload = repo.findById(id).orElseThrow(() -> new RuntimeException("Data not found"));
		
		Buy bs = new Buy();
		
		bs.setUserName(userName);
		bs.setDescription(description);
		bs.setUpload(upload);                                                                                                                                                                                                                                                                                                                                                                                                                   
		rep.save(bs);

		return "Message sent successfully";
		
	}
	
	@GetMapping("/RoomOwner/messag")
	@PreAuthorize("hasRole('RoomOwner')")
	public List<Buy> getMessage(){
		String username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
				
				return rep.findMessagesWithRoom(username); 
			
	}
	
	// RoomOwner ka data
	@GetMapping("/RoomOwner/mydata")
	@PreAuthorize("hasRole('RoomOwner')")
	public List<Upload> getMyData() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return repo.findByOwnerName(username);
	}
	
	
	@GetMapping("/RoomOwner/messages") 
	@PreAuthorize("hasRole('RoomOwner')")
	public List<Buy> getMessages() {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    return rep.findMessagesWithRoom(username);  // 🔥 FIXED
	}
	
	@GetMapping("/user/rooms")
	@PreAuthorize("hasRole('USER')")
	public List<Upload> getAllRooms(){
		return repo.findAll();
	}

	@GetMapping("/rooms") // public
	public List<Upload> getAllRoom() {
	    return repo.findAll();
	}
	
}
