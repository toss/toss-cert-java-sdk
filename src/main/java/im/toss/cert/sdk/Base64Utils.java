package im.toss.cert.sdk;

import org.apache.commons.codec.binary.Base64;

class Base64Utils {
    static String encodeToString(byte[] src) {
        return Base64.encodeBase64String(src)
            .replace("\r", "")
            .replace("\n", "");
    }

    static byte[] decode(String src) {
        return Base64.decodeBase64(src);
    }
}
