package com.booking.app.model;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;


    @Column(unique = true)
    private String username;

    private String password;

    public User() {
    }

    public User(String name,  String username, String password) {
        this.name = name;
        this.username = username;
        this.setPassword(password);
    }

    public long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
