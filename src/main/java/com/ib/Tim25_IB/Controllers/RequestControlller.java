package com.ib.Tim25_IB.Controllers;

import com.ib.Tim25_IB.DTOs.CertificateListDTO;
import com.ib.Tim25_IB.DTOs.CertificateRequestDTO;
import com.ib.Tim25_IB.DTOs.EmailDTO;
import com.ib.Tim25_IB.DTOs.RequestListDTO;
import com.ib.Tim25_IB.model.Certificate;
import com.ib.Tim25_IB.model.CertificateRequest;
import com.ib.Tim25_IB.services.CertificateService;
import com.ib.Tim25_IB.services.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request")
public class RequestControlller {
    @Autowired
    RequestService requestService;

    //CREATE A REQUEST FOR A NEWWW CERTIFICATE
    //if the base cert for the new cert has the same user or if the user is an admin -> auto accept
    //else create a cert request
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCertificateRequest(@RequestBody CertificateRequestDTO request){
        requestService.createCertificateRequest(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //GET ALL THE ACTIVE AND PAST CERTIFICATE REQUESTS
    @PostMapping(value="/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getRequests(@RequestBody EmailDTO email){
        RequestListDTO list = requestService.getAll(email.getEmail());
        if(list != null){
            return new ResponseEntity<RequestListDTO>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }
    //ACCEPT/DENY REQUEST
    @PutMapping(value="/respond")
    public ResponseEntity<?> processRequest(){
//        userService.processCertificate();
        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
