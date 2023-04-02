package com.ib.Tim25_IB.services;

import com.ib.Tim25_IB.DTOs.UserLoginRequestDTO;
import com.ib.Tim25_IB.DTOs.UserRequestDTO;
import com.ib.Tim25_IB.model.User;

public class UserService {

    public User createUser(UserRequestDTO requestDTO){
        User user = new User(1L, requestDTO);
        // call repo to save
        return user;
    }

    public boolean loginUser(UserLoginRequestDTO requestDTO){
        //call repo to login
        return true;
    }

    public void getRequests() {
    }

    public void processCertificate() {
    }
}
