package com.ib.Tim25_IB.Controllers;

import com.ib.Tim25_IB.DTOs.CertificateDTO;
import com.ib.Tim25_IB.DTOs.CertificateListDTO;
import com.ib.Tim25_IB.DTOs.CertificateRequestDTO;
import com.ib.Tim25_IB.model.Certificate;
import com.ib.Tim25_IB.services.CertificateGenerator;
import com.ib.Tim25_IB.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    @Autowired
    CertificateService certificateService;

    @Autowired
    private CertificateGenerator certificateGenerator;

    @PostMapping
    public ResponseEntity<Certificate> issueCertificate(@RequestBody CertificateRequestDTO request) {
        try {
            Certificate certificate = certificateGenerator.issueCertificate(
                    request.getIssuerSN(),
                    request.getSubjectUsername(),
                    request.getKeyUsageFlags(),
                    LocalDateTime.parse(request.getValidTo())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(certificate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


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
