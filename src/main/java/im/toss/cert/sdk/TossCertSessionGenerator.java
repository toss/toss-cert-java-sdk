package im.toss.cert.sdk;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TossCertSessionGenerator {
    private final static String version = "v1";

    private final RSACipher rsaCipher;

    public TossCertSessionGenerator(String publicKeyString) {
        try {
            this.rsaCipher = new RSACipher(publicKeyString);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public TossCertSession generate() {
        return generate(AESAlgorithm.AES_GCM);
    }

    public TossCertSession generate(AESAlgorithm algorithm) {
        try {
            String id = UUID.randomUUID().toString();
            String secretKey = SecureKeyGenerator.generateKey(256);
            String iv = SecureKeyGenerator.generateKey(128);
            String encryptedSessionKey = buildEncryptSessionKeyPart(algorithm, secretKey, iv);
            return new TossCertSession(version, id, algorithm, secretKey, iv, encryptedSessionKey);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public TossCertSession deserialize(String serializedSessionKey) {
        try {
            String[] fields = serializedSessionKey.split(TossCertSession.separatorRegEx);
            AESAlgorithm algorithm = AESAlgorithm.valueOf(fields[2]);
            String secretKey = fields[3];
            String iv = fields[4];
            String encryptedSessionKey = buildEncryptSessionKeyPart(algorithm, secretKey, iv);
            return new TossCertSession(fields[0], fields[1], algorithm, secretKey, iv, encryptedSessionKey);
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private String buildEncryptSessionKeyPart(AESAlgorithm algorithm, String secretKey, String iv) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String part = StringUtils.join(TossCertSession.separator, new String[]{algorithm.name(), secretKey, iv});
        return rsaCipher.encrypt(part);
    }
}
