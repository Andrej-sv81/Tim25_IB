package com.ib.Tim25_IB.services;

import com.ib.Tim25_IB.DTOs.CertificateDTO;
import com.ib.Tim25_IB.DTOs.CertificateListDTO;
import com.ib.Tim25_IB.DTOs.CertificateRequestDTO;
import com.ib.Tim25_IB.Repository.RequestRepository;
import com.ib.Tim25_IB.model.CertificateRequest;
import com.ib.Tim25_IB.model.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
@Service
public class CertificateService {

    @Autowired
    RequestRepository requestRepository;

    public CertificateListDTO getAll(){
        List<CertificateDTO> certificateList = null; //get from repo
        CertificateListDTO returnList = new CertificateListDTO(certificateList.size(), certificateList);
        return returnList;
    }

    public void validateCertificate(Long id) {
        //call repo to validate
    }

    public void createCertificateRequest(CertificateRequestDTO requestDTO) {
        CertificateRequest request = new CertificateRequest();
        request.setStatus(RequestStatus.PENDING);
        request.setIssuerSN(requestDTO.getIssuerSN());
        request.setSubjectUsername(requestDTO.getSubjectUsername());
        request.setKeyUsageFlags(requestDTO.getKeyUsageFlags());
        request.setValidTo(Date.from(LocalDateTime.parse(requestDTO.getValidTo()).atZone(ZoneId.systemDefault()).toInstant()));
        request.setStatusDeniedMessage("No message");

        requestRepository.save(request);
        requestRepository.flush();
    }
}
