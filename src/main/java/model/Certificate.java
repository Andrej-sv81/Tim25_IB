package model;

import java.time.LocalDateTime;

public class Certificate {

    private Long id;
    public String serialNumber;
    public String signatureAlgorithm;
    public String issuer;
    public LocalDateTime validTo;
    public LocalDateTime validFrom;
    public CertificateStatus certificateStatus;
    public CertificateType certificateType;
    public String username;

    public Certificate() {
    }

    public Certificate(Long id, String serialNumber, String signatureAlgorithm, String issuer, LocalDateTime validTo, LocalDateTime validFrom, CertificateStatus certificateStatus, CertificateType certificateType, String username) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.signatureAlgorithm = signatureAlgorithm;
        this.issuer = issuer;
        this.validTo = validTo;
        this.validFrom = validFrom;
        this.certificateStatus = certificateStatus;
        this.certificateType = certificateType;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public LocalDateTime getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDateTime validTo) {
        this.validTo = validTo;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public CertificateStatus getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(CertificateStatus certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public CertificateType getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(CertificateType certificateType) {
        this.certificateType = certificateType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
