package com.example.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "upload")
public class Upload {

    private String upload;
    private Integer price;
    private Long contact;
    private String address;
    private String facility;
    private String description;
    private String ownerName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "upload", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  
    @JsonManagedReference 
    private List<Buy> buys;

    public Upload(String upload, Integer price, Long contact, String address, String facility, String description, Long id) {
        this.upload = upload;
        this.price = price;
        this.contact = contact;
        this.address = address;
        this.facility = facility;
        this.description = description;
        this.id = id;
    }

    public Upload() {
        super();
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Buy> getBuys() {
        return buys;
    }

    public void setBuys(List<Buy> buys) {
        this.buys = buys;
    }
 
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}