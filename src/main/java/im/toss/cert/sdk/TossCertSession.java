package im.toss.cert.sdk;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TossCertSession {
    private final String version;
    private final String id;
    private final AESAlgorithm algorithm;
    private final String secretKey;
    private final String iv;

    private final String encryptedSessionKey;
    private final AESCipher aesCipher;

    static final String separator = "$";
    static final String separatorRegEx = "\\$";

    TossCertSession(
            String version,
            String id,
            AESAlgorithm algorithm,
            String secretKey,
            String iv,
            String encryptedSessionKey
    ) {
        this.version = version;
        this.id = id;
        this.algorithm = algorithm;
        this.secretKey = secretKey;
        this.iv = iv;
        this.encryptedSessionKey = encryptedSessionKey;
        this.aesCipher = new AESCipher(secretKey, iv, algorithm);
    }

    public String getSessionKey() {
        return addMeta(encryptedSessionKey);
    }

    public String encrypt(String plainText) {
        try {
            String encrypted = aesCipher.encrypt(plainText);
            String hash = calculateHash(plainText);
            return addMeta(StringUtils.join(separator, new String[]{encrypted, hash}));
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e.getCause());
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

    public String decrypt(String encryptedText) {
        try {
            String[] items = encryptedText.split(separatorRegEx);
            if (items.length < 3) {
                throw new RuntimeException("암호 문자열 포맷이 다릅니다");
            }
            if (!version.equals(items[0])) {
                throw new RuntimeException(String.format("세션 키 버전이 다릅니다. 세션 키 버전: %s != 암호 문자열 버전: %s", version, items[0]));
            }
            if (!id.equals(items[1])) {
                throw new RuntimeException(String.format("세션 키 id 이 다릅니다. 세션 키 버전: %s != 암호 문자열 id 버전: %s", id, items[1]));
            }
            String plainText = aesCipher.decrypt(items[2]);
            verify(plainText, items);
            return plainText;
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e.getCause());
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

    public String serializeSession() {
        return StringUtils.join(separator, new String[]{version, id, algorithm.name(), secretKey, iv});
    }

    private void verify(String plainText, String[] items) throws NoSuchAlgorithmException, InvalidKeyException {
        if (algorithm == AESAlgorithm.AES_GCM) return;

        String calculatedHash = HMAC.calculateHash(secretKey, plainText);
        if (items.length != 4 || !calculatedHash.equals(items[3])) {
            throw new RuntimeException("AES_CBC 무결성 검증 실패");
        }
    }

    private String calculateHash(String plainText) throws NoSuchAlgorithmException, InvalidKeyException {
        if (algorithm == AESAlgorithm.AES_GCM) return "";
        else return HMAC.calculateHash(secretKey, plainText);
    }

    private String addMeta(String data) {
        return StringUtils.join(separator, new String[]{version, id, data});
    }
}
