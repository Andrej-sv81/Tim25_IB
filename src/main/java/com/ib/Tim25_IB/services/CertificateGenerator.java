package com.ib.Tim25_IB.services;

import Repository.CertificateRepository;
import Repository.UserRepository;
import com.ib.Tim25_IB.model.Certificate;
import com.ib.Tim25_IB.model.CertificateStatus;
import com.ib.Tim25_IB.model.CertificateType;
import com.ib.Tim25_IB.model.User;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CertificateGenerator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    private static final String CERT_DIR = "certs";

    private Certificate issuer;
    private User subject;
    private KeyUsage keyUsage;
    private boolean isAuthority;
    private X509Certificate issuerCertificate;
    private Date validTo;
    private KeyPair currentKeyPair;

    public Certificate issueCertificate(String issuerSN, String subjectUsername, String keyUsageFlags, LocalDateTime validTo) throws Exception {
        validate(issuerSN, subjectUsername, keyUsageFlags, validTo);
        X509Certificate cert = generateCertificate();

        return exportGeneratedCertificate(cert);
    }

    private Certificate exportGeneratedCertificate(X509Certificate cert) throws IOException, CertificateEncodingException {
        Certificate certificateForDb = new Certificate();
        certificateForDb.setIssuer(issuer != null ? issuer.getSerialNumber() : null);
        certificateForDb.setCertificateStatus(CertificateStatus.VALID);
        certificateForDb.setCertificateType(isAuthority
                ? issuerCertificate == null ? CertificateType.ROOT : CertificateType.INTERMEDIATE
                : CertificateType.END);
        certificateForDb.setSerialNumber(cert.getSerialNumber().toString());
        certificateForDb.setSignatureAlgorithm(cert.getSigAlgName());
        certificateForDb.setUsername(subject.getUsername());
        certificateForDb.setValidFrom(cert.getNotBefore());
        certificateForDb.setValidTo(cert.getNotAfter());

        certificateRepository.save(certificateForDb);

        Files.write(new File(CERT_DIR, certificateForDb.getSerialNumber() + ".crt").toPath(), cert.getEncoded());
        try (FileOutputStream fos = new FileOutputStream(new File(CERT_DIR, certificateForDb.getSerialNumber() + ".key"))) {
            fos.write(currentKeyPair.getPrivate().getEncoded());
        }

        return certificateForDb;
    }

    private PrivateKey issuerPrivateKey;

    private void validate(String issuerSN, String subjectUsername, String keyUsageFlags, LocalDateTime validTo) throws Exception {
        if (issuerSN != null && !issuerSN.isEmpty()) {
            issuer = certificateRepository.findBySerialNumber(issuerSN);
            issuerCertificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(Files.newInputStream(new File(CERT_DIR, issuerSN + ".crt").toPath()));

            byte[] privateKeyBytes = Files.readAllBytes(new File(CERT_DIR, issuerSN + ".key").toPath());
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            issuerPrivateKey = keyFactory.generatePrivate(privateKeySpec);
            issuerCertificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(Files.newInputStream(new File(CERT_DIR, issuerSN + ".crt").toPath()));
            JcaX509CertificateHolder issuerCertificateHolder = new JcaX509CertificateHolder(issuerCertificate);
            issuerCertificate = new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider()).getCertificate(issuerCertificateHolder);
        }


        Date currentDate = new Date();
        if (!(validTo.atZone(ZoneId.systemDefault()).toInstant().isAfter(currentDate.toInstant()) && (issuerSN == null || validTo.atZone(ZoneId.systemDefault()).toInstant().isBefore(issuerCertificate.getNotAfter().toInstant())))) {
            throw new Exception("The date is not in the accepted range");
        }
        this.validTo = Date.from(validTo.atZone(ZoneId.systemDefault()).toInstant());

        subject = userRepository.findByUsername(subjectUsername);
        keyUsage = parseFlags(keyUsageFlags);
    }

    private X509Certificate generateCertificate() throws NoSuchAlgorithmException, OperatorCreationException, CertificateException, IOException {
        X500Name subjectText = new X500Name("CN=" + subject.getUsername());
        currentKeyPair = generateKeyPair(4096);

        X509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                issuerCertificate == null ? subjectText : new JcaX509CertificateHolder(issuerCertificate).getSubject(),
                new BigInteger(Long.toString(System.currentTimeMillis() + SecureRandom.getInstanceStrong().nextLong())),
                new Date(),
                validTo,
                subjectText,
                currentKeyPair.getPublic())
                .addExtension(Extension.basicConstraints, true, new BasicConstraints(isAuthority))
                .addExtension(Extension.keyUsage, true, keyUsage);

        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSAEncryption").build(issuerPrivateKey != null ? issuerPrivateKey : currentKeyPair.getPrivate());

        org.bouncycastle.cert.X509CertificateHolder certificateHolder = certificateBuilder.build(contentSigner);
        return new JcaX509CertificateConverter().setProvider(new BouncyCastleProvider()).getCertificate(certificateHolder);
    }

    private KeyPair generateKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize, new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    private KeyUsage parseFlags(String keyUsageFlags) throws Exception {
        if (keyUsageFlags == null || keyUsageFlags.isEmpty()) {
            throw new Exception("KeyUsageFlags are mandatory");
        }

        String[] flagArray = keyUsageFlags.split(",");
        int keyUsageBits = 0;

        for (String flag : flagArray) {
            try {
                int index = Integer.parseInt(flag);
                keyUsageBits |= (1 << index);

                if (index == 5) { // KeyCertSign
                    isAuthority = true;
                }
            } catch (NumberFormatException e) {
                throw new Exception("Unknown flag: " + flag);
            }
        }

        return new KeyUsage(keyUsageBits);
    }

}
