package im.toss.cert.sdk;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

public class TossCertSessionGenerator {
    private final static String version = "v1_0.0.14";
    private final static String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoVdxG0Qi9pip46Jw9ImSlPVD8+L2mM47ey6EZna7D7utgNdh8Tzkjrm1Yl4h6kPJrhdWvMIJGS51+6dh041IXcJEoUquNblUEqAUXBYwQM8PdfnS12SjlvZrP4q6whBE7IV1SEIBJP0gSK5/8Iu+uld2ctJiU4p8uswL2bCPGWdvVPltxAg6hfAG/ImRUKPRewQsFhkFvqIDCpO6aeaR10q6wwENZltlJeeRnl02VWSneRmPqqypqCxz0Y+yWCYtsA+ngfZmwRMaFkXcWjaWnvSqqV33OAsrQkvuBHWoEEkvQ0P08+h9Fy2+FhY9TeuukQ2CVFz5YyOhp25QtWyQI+IaDKk+hLxJ1APR0c3tmV0ANEIjO6HhJIdu2KQKtgFppvqSrZp2OKtI8EZgVbWuho50xvlaPGzWoMi9HSCb+8ARamlOpesxHH3O0cTRUnft2Zk1FHQb2Pidb2z5onMEnzP2xpTqAIVQyb6nMac9tof5NFxwR/c4pmci+1n8GFJIFN18j2XGad1mNyio/R8LabqnzNwJC6VPnZJz5/pDUIk9yKNOY0KJe64SRiL0a4SNMohtyj6QlA/3SGxaEXb8UHpophv4G9wN1CgfyUamsRqp8zo5qDxBvlaIlfkqJvYPkltj7/23FHDjPi8q8UkSiAeu7IV5FTfB5KsiN8+sGSMCAwEAAQ==";

    private final RSACipher rsaCipher;

    public TossCertSessionGenerator() {
        this(publicKey);
    }

    public TossCertSessionGenerator(String publicKeyString) {
        try {
            this.rsaCipher = new RSACipher(publicKeyString);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getCause());
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    public TossCertSession generate() {
        return generate(AESAlgorithm.AES_GCM);
    }

    public TossCertSession generateCBC128() {
        return generate(AESAlgorithm.AES_CBC, 128, 128);
    }

    public TossCertSession generate(AESAlgorithm algorithm) {
        if (algorithm == AESAlgorithm.AES_GCM) {
            return generate(algorithm, 256, 96);
        } else {
            return generate(algorithm, 256, 128);
        }
    }

    private TossCertSession generate(AESAlgorithm algorithm, int keyLength, int ivLength) {
        try {
            String id = UUID.randomUUID().toString();
            String secretKey = SecureKeyGenerator.generateKey(keyLength);
            String iv;
            if (algorithm == AESAlgorithm.AES_GCM) {
                iv = SecureKeyGenerator.generateRandomBytes(ivLength);
            } else {
                iv = SecureKeyGenerator.generateKey(ivLength);
            }
            String encryptedSessionKey = buildEncryptSessionKeyPart(algorithm, secretKey, iv);
            return new TossCertSession(version, id, algorithm, secretKey, iv, encryptedSessionKey);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e.getCause());
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e.getCause());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getCause());
        } catch (BadPaddingException e) {
            throw new RuntimeException(e.getCause());
        } catch (InvalidKeyException e) {
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
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e.getCause());
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e.getCause());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getCause());
        } catch (BadPaddingException e) {
            throw new RuntimeException(e.getCause());
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private String buildEncryptSessionKeyPart(AESAlgorithm algorithm, String secretKey, String iv) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String part = StringUtils.join(TossCertSession.separator, new String[]{algorithm.name(), secretKey, iv});
        return rsaCipher.encrypt(part);
    }
}
