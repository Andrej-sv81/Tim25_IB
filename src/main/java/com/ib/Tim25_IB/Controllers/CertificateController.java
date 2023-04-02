package com.ib.Tim25_IB.Controllers;

import com.ib.Tim25_IB.DTOs.CertificateDTO;
import com.ib.Tim25_IB.DTOs.CertificateListDTO;
import com.ib.Tim25_IB.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    CertificateService certificateService;
    // RETURN ALL CERTIFICATES
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
        //return list of Certificates
        CertificateListDTO certList = certificateService.getAll();
        return new ResponseEntity<CertificateListDTO>(certList, HttpStatus.OK);
    }

    // VALIDATE A CERTIFICATE WITH ITS ID
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateCertificate(@RequestParam Long id){
    certificateService.validateCertificate(id);
    return new ResponseEntity<>(HttpStatus.OK);
    }

    //CREATE A REQUEST FOR A NEWWW CERTIFICATE
    @PostMapping(value="/request", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCertificateRequest(@RequestBody CertificateDTO request){
        certificateService.createCertificateRequest();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
