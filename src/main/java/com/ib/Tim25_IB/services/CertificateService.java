package com.ib.Tim25_IB.services;

import com.ib.Tim25_IB.DTOs.CertificateDTO;
import com.ib.Tim25_IB.DTOs.CertificateListDTO;
import com.ib.Tim25_IB.Repository.CertificateRepository;
import com.ib.Tim25_IB.model.Certificate;
import com.ib.Tim25_IB.model.CertificateStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class CertificateService {

    @Autowired
    CertificateRepository certificateRepository;

    public CertificateListDTO getAll(){
        List<CertificateDTO> certificateList = null; //get from repo
        CertificateListDTO returnList = new CertificateListDTO(certificateList.size(), certificateList);
        return returnList;
    }

    public boolean validateCertificate(String id) {
        try {
            Certificate cert = certificateRepository.findBySerialNumber(id);
            return cert.getCertificateStatus() == CertificateStatus.VALID;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
