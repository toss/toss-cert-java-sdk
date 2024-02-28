package im.toss.cert.sdk;

import org.bouncycastle.cert.jcajce.JcaCertStoreBuilder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;

import java.security.GeneralSecurityException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Collection;

public class PKCS7CertificateExtractor {
    public static String extractCertificate(String pkcs7Data) {
        try {
            CMSSignedData signedData = new CMSSignedData(Base64Utils.decode(pkcs7Data));
            CertStore certStore = new JcaCertStoreBuilder().addCertificates(signedData.getCertificates()).build();
            Collection<? extends Certificate> certificates = certStore.getCertificates(null);
            return convertToPem(certificates.iterator().next());
        } catch (CMSException e) {
            throw new RuntimeException(e.getCause());
        } catch (CertificateEncodingException e) {
            throw new RuntimeException(e.getCause());
        } catch (CertStoreException e) {
            throw new RuntimeException(e.getCause());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private static String convertToPem(Certificate certificate) throws CertificateEncodingException {
        String pemCertPre = "-----BEGIN CERTIFICATE-----\n";
        String pemCertPost = "\n-----END CERTIFICATE-----";
        String pemCert = Base64Utils.encodeToString(certificate.getEncoded());
        return pemCertPre + pemCert + pemCertPost;
    }
}
