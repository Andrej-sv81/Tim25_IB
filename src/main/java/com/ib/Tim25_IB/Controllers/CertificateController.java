package com.ib.Tim25_IB.Controllers;

import com.ib.Tim25_IB.DTOs.*;
import com.ib.Tim25_IB.model.Certificate;
import com.ib.Tim25_IB.services.CertificateGenerator;
import com.ib.Tim25_IB.services.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Certificate>> getCertificates() throws IOException {
        List<Certificate> certificates = certificateService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(certificates);
    }


    @PostMapping("/root")
    public ResponseEntity<Certificate> rootCertificate(@RequestBody RootCertifaceDTO request) {

        try {
            Certificate certificate = certificateGenerator.rootIssueCertificate(
                    request.getSubjectUsername(),
                    request.getKeyUsageFlags(),
                    LocalDateTime.parse(request.getValidTo())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(certificate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // VALIDATE A CERTIFICATE WITH ITS ID
    @PostMapping(value = "/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> validateCertificate(@RequestBody CertIdDTO id){
        boolean valid = certificateService.validateCertificate(id.getSerialNumber());
        return new ResponseEntity<Boolean>(valid, HttpStatus.OK);
    }

}
