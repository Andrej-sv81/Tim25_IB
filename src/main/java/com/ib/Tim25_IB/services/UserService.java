package com.ib.Tim25_IB.services;

import com.ib.Tim25_IB.Repository.UserRepository;
import com.ib.Tim25_IB.DTOs.UserLoginRequestDTO;
import com.ib.Tim25_IB.DTOs.UserRequestDTO;
import com.ib.Tim25_IB.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserRequestDTO requestDTO) throws IOException {
        User user = new User(1L, requestDTO);
        List<User> users = userRepository.getAllUsers();
        users.add(user);
        userRepository.save(users);
        return user;
    }

    public boolean loginUser(UserLoginRequestDTO requestDTO) throws IOException {
        List<User> users = userRepository.getAllUsers();
        for (User user : users){
            if(user.getEmail().equals(requestDTO.getEmail()) && user.getPassword().equals(requestDTO.getPassword())){
                return true;
            }
        }
        return false;
    }

    public void getRequests() {
    }

    public void processCertificate() {
    }
}
