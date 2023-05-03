package com.ib.Tim25_IB.model;

import com.ib.Tim25_IB.DTOs.UserRequestDTO;

public class User {

    private Long id;
    private String username;
    private String name;
    private String lastname;
    private String phoneNumber;
    private String password;
    private boolean isAdmin;

    public User() {
    }

    public User(Long id, String username, String name, String lastname, String phoneNumber, String password,boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(Long id, UserRequestDTO request){
        this.id = id;
        this.username = request.getEmail();
        this.name = request.getName();
        this.lastname = request.getLastname();
        this.phoneNumber = request.getPhoneNumber();
        this.password = request.getPassword();
        this.isAdmin = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
