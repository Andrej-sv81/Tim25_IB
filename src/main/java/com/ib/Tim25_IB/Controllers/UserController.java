package com.ib.Tim25_IB.Controllers;

import com.ib.Tim25_IB.DTOs.UserLoginRequestDTO;
import com.ib.Tim25_IB.DTOs.UserRequestDTO;
import com.ib.Tim25_IB.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.RescaleOp;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;
    //REGISTER A NEW USER
    @PostMapping(value="/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO request){

        userService.createUser(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //LOG IN AN EXISTING USER
    @PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDTO request){

        userService.loginUser(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //GET ALL THE ACTIVE AND PAST CERTIFICATE REQUESTS
    @GetMapping(value="/requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRequests(){
        userService.getRequests();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //ACCEPT/DENY REQUEST
    @PutMapping(value="/respond")
    public ResponseEntity<?> processRequest(){
        userService.processCertificate();
        return  new ResponseEntity<>(HttpStatus.OK);
    }

}
