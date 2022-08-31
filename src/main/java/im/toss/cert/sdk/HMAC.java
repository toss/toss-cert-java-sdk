package im.toss.cert.sdk;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class HMAC {
    private final static String algorithm = "HmacSHA256";

    static String calculateHash(String secret, String message) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256Hmac = Mac.getInstance(algorithm);
        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(Constants.charset), algorithm);
        sha256Hmac.init(secretKey);
        return bytesToHex(sha256Hmac.doFinal(message.getBytes(Constants.charset)));
    }

    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
}
