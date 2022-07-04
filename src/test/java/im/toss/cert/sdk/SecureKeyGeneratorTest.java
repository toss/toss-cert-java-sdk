package im.toss.cert.sdk;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

class SecureKeyGeneratorTest {

    @Test
    void test() throws NoSuchAlgorithmException {
        String key = SecureKeyGenerator.generateKey(256);
        System.out.println("key:" + key);
    }
}
