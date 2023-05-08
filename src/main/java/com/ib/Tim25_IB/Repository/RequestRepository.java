package com.ib.Tim25_IB.Repository;

import com.ib.Tim25_IB.DTOs.EmailDTO;
import com.ib.Tim25_IB.model.CertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<CertificateRequest, Long> {

    List<CertificateRequest> findAllBySubjectUsername(String email);
}
