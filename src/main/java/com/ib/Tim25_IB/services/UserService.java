package com.ib.Tim25_IB.services;

import com.ib.Tim25_IB.Repository.UserRepository;
import com.ib.Tim25_IB.DTOs.UserLoginRequestDTO;
import com.ib.Tim25_IB.DTOs.UserRequestDTO;
import com.ib.Tim25_IB.model.User;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
//    public BCryptPasswordEncoder passwordEncoderUser() {
//        return new BCryptPasswordEncoder();
//    }
    public void createUser(UserRequestDTO requestDTO) throws IOException {
        User user = new User(requestDTO);
//        user.setPassword(passwordEncoderUser().encode(user.getPassword()));
        userRepository.save(user);
        userRepository.flush();
    }

    public void createAdmin(UserRequestDTO requestDTO) throws IOException {
        User user = new User(requestDTO);
        user.setAdmin(true);
        userRepository.save(user);
        userRepository.flush();
    }

    public boolean loginUser(UserLoginRequestDTO requestDTO) throws IOException {
        Optional<User> found = Optional.ofNullable(userRepository.findByEmail(requestDTO.getEmail()));
        if(found.isPresent() && found.get().getPassword().equals(requestDTO.getPassword())){
            return true;
        }else{
            return false;
        }
    }

    public boolean isUserAdmin(String email){
        return userRepository.findByEmail(email).isAdmin();
    }

    public void getRequests() {
    }

    public void processCertificate() {
    }
}
