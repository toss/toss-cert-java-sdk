package im.toss.cert.sdk;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

class AESCipher {
    private final SecretKeySpec secretKey;
    private final AlgorithmParameterSpec ivSpec;
    private final AESAlgorithm algorithm;

    AESCipher(String secretKey, String iv) {
        this(secretKey, iv, AESAlgorithm.AES_GCM);
    }

    AESCipher(String secretKey, String iv, AESAlgorithm algorithm) {
        this.secretKey = new SecretKeySpec(Base64Utils.decode(secretKey), "AES");
        if (algorithm == AESAlgorithm.AES_GCM) {
            this.ivSpec = new GCMParameterSpec(16 * java.lang.Byte.SIZE, Base64Utils.decode(iv));
        } else {
            this.ivSpec = new IvParameterSpec(Base64Utils.decode(iv));
        }
        this.algorithm = algorithm;
    }

    String encrypt(String plainText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
        byte[] cipherText = cipher.doFinal(plainText.getBytes(Constants.charset));
        return Base64Utils.encodeToString(cipherText);
    }

    String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
        byte[] cipherText = cipher.doFinal(Base64Utils.decode(encryptedText));
        return new String(cipherText, Constants.charset);
    }

    private Cipher getCipher(int opMode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithm.algorithmName);
        cipher.init(opMode, secretKey, ivSpec);
        if (algorithm == AESAlgorithm.AES_GCM) {
            cipher.updateAAD(secretKey.getEncoded());
        }
        return cipher;
    }
}
