package im.toss.cert.sdk;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class TossCertSessionGenerator {
    private final static String version = "v1";
    private final static String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoVdxG0Qi9pip46Jw9ImSlPVD8+L2mM47ey6EZna7D7utgNdh8Tzkjrm1Yl4h6kPJrhdWvMIJGS51+6dh041IXcJEoUquNblUEqAUXBYwQM8PdfnS12SjlvZrP4q6whBE7IV1SEIBJP0gSK5/8Iu+uld2ctJiU4p8uswL2bCPGWdvVPltxAg6hfAG/ImRUKPRewQsFhkFvqIDCpO6aeaR10q6wwENZltlJeeRnl02VWSneRmPqqypqCxz0Y+yWCYtsA+ngfZmwRMaFkXcWjaWnvSqqV33OAsrQkvuBHWoEEkvQ0P08+h9Fy2+FhY9TeuukQ2CVFz5YyOhp25QtWyQI+IaDKk+hLxJ1APR0c3tmV0ANEIjO6HhJIdu2KQKtgFppvqSrZp2OKtI8EZgVbWuho50xvlaPGzWoMi9HSCb+8ARamlOpesxHH3O0cTRUnft2Zk1FHQb2Pidb2z5onMEnzP2xpTqAIVQyb6nMac9tof5NFxwR/c4pmci+1n8GFJIFN18j2XGad1mNyio/R8LabqnzNwJC6VPnZJz5/pDUIk9yKNOY0KJe64SRiL0a4SNMohtyj6QlA/3SGxaEXb8UHpophv4G9wN1CgfyUamsRqp8zo5qDxBvlaIlfkqJvYPkltj7/23FHDjPi8q8UkSiAeu7IV5FTfB5KsiN8+sGSMCAwEAAQ==";

    private final RSACipher rsaCipher;

    public TossCertSessionGenerator() {
        this(publicKey);
    }

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