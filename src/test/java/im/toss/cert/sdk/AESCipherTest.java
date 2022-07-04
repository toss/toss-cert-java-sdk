package im.toss.cert.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class AESCipherTest {
    private final String key = "SqdGyDwgKZNLsSFXtq7zANXUBZ/co1RLDSD+rBJOW1E=";
    private final String iv = "RVlJy3sZ5II9nQTOe3Hg+Q==";

    @Test
    void test() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String plainText = "Test Plain String";
        AESCipher aesCipher = new AESCipher(key, iv);

        String encrypted = aesCipher.encrypt(plainText);
        System.out.println("encrypted: " + encrypted);
        String decrypted = aesCipher.decrypt(encrypted);
        System.out.println("decrypted: " + decrypted);

        Assertions.assertEquals(decrypted, plainText);
    }
}
