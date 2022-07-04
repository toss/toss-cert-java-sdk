package im.toss.cert.sdk;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class RSACipherTest {
    private final String publicKeyString = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoNsnNuQYnGR2v3XSJc2VNxYSBsykuSLJiOeSbWBSEmNG7Fm48rpzZ5bS2diRjHL55ZWBmtJ2k2WqIcHAQS8VnzhFM+bawDwfk6O8xT6hUr+C6ELENWlk2cJchAuGs5HU5bORorIqU+zuhV3kCj7XcZTIYJepWvK2OoIHORH6YkuISpuQUn1ekY11Da/wiZDdLaz7HhKFmkXdV81C13MnsCj8+81akZSupB2QNBbIW3KOVVlfB9zK+3uFoZCqn68C1iZB0u0BCNFF3/N3mNN3tXTmCtSBD+W8+yxowYM4t9ifX19m6sV1mzrGq0/wsXR5jbizAFe79vONuSS7GnG05SO+ZQe/zg9vh3upuQ9P/nmMvRzpTzXJJ9IliNQCboSpIOsbi5IcNqi1G8EVeKNd3IcK3Z5v8nvEpRqn5vD1MU3FNshrHRPxBNZfz3/6NWjlS+af01Q2Y1NYRtuGJNtIUXjHeexkVZycgI7feXs3XtG+yjJU6Z1SnXj5HJbMneCiQbg/aTMsHG57m8+t5QdA+Zx1Gkz3uxpl92/qzZIWV7JwXbR0l2GmSM8I397zT8e2rlIWmQ8XUESjXdmWWBMR2Oqri8drfzHzvfVG5W2D8lcmyHW2yLJ/JBQNK1CJH+NAwJaOmK5sQK8sSLtp8axcveKN+3Dn1kWDrJqk5O90CfUCAwEAAQ==";

    @Test
    void test() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        RSACipher rsaCipher = new RSACipher(publicKeyString);
        String encrypted = rsaCipher.encrypt("Test Plain String");
        System.out.println("encrypted: " + encrypted);
    }
}
