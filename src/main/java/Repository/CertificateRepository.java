package Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ib.Tim25_IB.Controllers.CertificateController;
import com.ib.Tim25_IB.model.Certificate;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertificateRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Certificate> getAllCertificates() throws IOException {
        File file = new File("certificate.json");
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<Certificate>>() {});
    }

    public void save(Certificate certificate) throws IOException {
        File file = new File("certificate.json");
        List<Certificate> certificates = getAllCertificates();
        certificates.add(certificate);
        objectMapper.writeValue(file, certificates);
    }

    public void saveAll(List<Certificate> certificates) throws IOException {
        File file = new File("certificate.json");
        objectMapper.writeValue(file, certificates);
    }

    public Certificate findBySerialNumber(String serialNum) throws IOException {
        List<Certificate> certificates = getAllCertificates();
        for (Certificate certificate: certificates) {
            if(certificate.getSerialNumber().equals(serialNum)){
                return certificate;
            }
        }
        return null;
    }
}
