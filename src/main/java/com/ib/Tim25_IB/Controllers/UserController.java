package com.ib.Tim25_IB.Controllers;

import com.ib.Tim25_IB.DTOs.UserLoginRequestDTO;
import com.ib.Tim25_IB.DTOs.UserRequestDTO;
import com.ib.Tim25_IB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    //REGISTER A NEW USER
    @PostMapping(value="/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO request) throws IOException {

        userService.createUser(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/register/admin",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerAdmin(@RequestBody UserRequestDTO request) throws IOException {

        userService.createAdmin(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //LOG IN AN EXISTING USER
    @PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDTO request) throws IOException {
        boolean check = userService.loginUser(request);
        if (check)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
