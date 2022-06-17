package im.toss.cert.sdk;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class SecureKeyGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();

    static String generateKey(int aesKeyBitLength) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(aesKeyBitLength, secureRandom);
        return Base64Utils.encodeToString(keyGenerator.generateKey().getEncoded());
    }

    static String generateRandomBytes(int lengthInBits) {
        byte[] bytes = new byte[lengthInBits / 8];
        secureRandom.nextBytes(bytes);
        return Base64Utils.encodeToString(bytes);
    }
}
