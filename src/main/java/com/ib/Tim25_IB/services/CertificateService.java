package com.ib.Tim25_IB.services;

import com.ib.Tim25_IB.DTOs.CertificateDTO;
import com.ib.Tim25_IB.DTOs.CertificateListDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CertificateService {

    public CertificateListDTO getAll(){
        List<CertificateDTO> certificateList = null; //get from repo
        CertificateListDTO returnList = new CertificateListDTO(certificateList.size(), certificateList);
        return returnList;
    }

    public void validateCertificate(Long id) {
        //call repo to validate
    }

    public void createCertificateRequest() {
    }
}
