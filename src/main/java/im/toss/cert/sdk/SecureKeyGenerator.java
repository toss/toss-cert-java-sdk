package im.toss.cert.sdk;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class SecureKeyGenerator {
    static String generateKey(int aesKeyBitLength) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(aesKeyBitLength, new SecureRandom());
        return Base64Utils.encodeToString(keyGenerator.generateKey().getEncoded());
    }
}
