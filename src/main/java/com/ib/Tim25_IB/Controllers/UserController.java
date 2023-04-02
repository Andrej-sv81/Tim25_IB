package com.ib.Tim25_IB.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO){
        //Check if saved
        //call service method and save user to file
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
