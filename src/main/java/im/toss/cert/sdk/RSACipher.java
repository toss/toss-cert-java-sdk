package im.toss.cert.sdk;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

class RSACipher {
    private final PublicKey publicKey;

    RSACipher(String base64PublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.publicKey = getPublicKeyFromBase64Encrypted(base64PublicKey);
    }

    static private PublicKey getPublicKeyFromBase64Encrypted(String base64PublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] decodedBase64PubKey = Base64Utils.decode(base64PublicKey);

        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedBase64PubKey));
    }

    String encrypt(String plainText)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes(Constants.charset));
        return Base64Utils.encodeToString(bytePlain);
    }
}
