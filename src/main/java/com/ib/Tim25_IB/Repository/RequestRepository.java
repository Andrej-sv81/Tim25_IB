package com.ib.Tim25_IB.Repository;

import com.ib.Tim25_IB.model.CertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<CertificateRequest, Long> {

}
