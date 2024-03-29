package com.booking.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    private String name;
    private String address;
    private int rating;
    private boolean status;

    @JsonBackReference
    @ManyToOne
    private User manager;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy="hotel", orphanRemoval = true)
    @MapKeyColumn(name="id")
    private Map<Long, Room> rooms = new HashMap<Long, Room>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy="hotel", orphanRemoval = true)
    @MapKeyColumn(name="id")
    private Map<Long, com.booking.app.model.Comment> comments = new HashMap<Long, com.booking.app.model.Comment>();

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy="hotel", orphanRemoval = true)
    @MapKeyColumn(name="id")
    private Map<Long, Image> images = new HashMap<Long, Image>();

    public Hotel() {}

    public Hotel(String name, String address, int rating, boolean status) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.status = false;
    }

    public String getAddress() {
        return address;
    }

    public Map<Long, com.booking.app.model.Comment> getComments() {
        return comments;
    }

    public Map<Long, Image> getImages() {
        return images;
    }

    public User getManager() {
        return manager;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public Map<Long, Room> getRooms() {
        return rooms;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public void setComments(Map<Long, Comment> comments) {
        this.comments = comments;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImages(Map<Long, Image> images) {
        this.images = images;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setRooms(Map<Long, Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + "\nName: " + getName() + "\nAddress: " + getAddress() + "\nRating: " + getRating() ;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
