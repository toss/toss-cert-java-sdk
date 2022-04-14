package im.toss.cert.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TossCertSessionTest {
    @Test
    public void test() {
        // 0. 사전에 전달받은 RSA 공개키 입니다.
        String publicKeyString = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoNsnNuQYnGR2v3XSJc2VNxYSBsykuSLJiOeSbWBSEmNG7Fm48rpzZ5bS2diRjHL55ZWBmtJ2k2WqIcHAQS8VnzhFM+bawDwfk6O8xT6hUr+C6ELENWlk2cJchAuGs5HU5bORorIqU+zuhV3kCj7XcZTIYJepWvK2OoIHORH6YkuISpuQUn1ekY11Da/wiZDdLaz7HhKFmkXdV81C13MnsCj8+81akZSupB2QNBbIW3KOVVlfB9zK+3uFoZCqn68C1iZB0u0BCNFF3/N3mNN3tXTmCtSBD+W8+yxowYM4t9ifX19m6sV1mzrGq0/wsXR5jbizAFe79vONuSS7GnG05SO+ZQe/zg9vh3upuQ9P/nmMvRzpTzXJJ9IliNQCboSpIOsbi5IcNqi1G8EVeKNd3IcK3Z5v8nvEpRqn5vD1MU3FNshrHRPxBNZfz3/6NWjlS+af01Q2Y1NYRtuGJNtIUXjHeexkVZycgI7feXs3XtG+yjJU6Z1SnXj5HJbMneCiQbg/aTMsHG57m8+t5QdA+Zx1Gkz3uxpl92/qzZIWV7JwXbR0l2GmSM8I397zT8e2rlIWmQ8XUESjXdmWWBMR2Oqri8drfzHzvfVG5W2D8lcmyHW2yLJ/JBQNK1CJH+NAwJaOmK5sQK8sSLtp8axcveKN+3Dn1kWDrJqk5O90CfUCAwEAAQ==";

        // 1. 세션 생성기를 사전에 생성해 주세요.
        TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator(publicKeyString);

        // 2. 인증 요청(개인정보가 포함된 경우) API 호출 전에, 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        // 3. 개인정보를 암호화 해주세요.
        String userName = "소중한 개인정보 입니다";
        String encryptedUserName = tossCertSession.encrypt(userName);
        System.out.println("encryptedUserName: " + encryptedUserName);

        // 4. 요청 파라미터에 추가해주세요.
         String sessionKey = tossCertSession.getSessionKey();
//         String userName = encryptedUserName;

        // 5. 응답을 받은 경우, 요청을 보낼 때 생성했던 tossCertSession 을 가지고 있어야 합니다.
        // encryptedUserName 가 응답을 받은 암호화된 userName 이라고 가정합니다.
        String decryptedUserName = tossCertSession.decrypt(encryptedUserName);

        // 6. decryptedUserName 은 무결성 검증까지 완료되어 있습니다.
        System.out.println("decryptedUserName: " + decryptedUserName);

        Assertions.assertEquals(decryptedUserName, userName);
    }

    @Test
    public void cbcTest() {
        // 0. 사전에 전달받은 RSA 공개키 입니다.
        String publicKeyString = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoNsnNuQYnGR2v3XSJc2VNxYSBsykuSLJiOeSbWBSEmNG7Fm48rpzZ5bS2diRjHL55ZWBmtJ2k2WqIcHAQS8VnzhFM+bawDwfk6O8xT6hUr+C6ELENWlk2cJchAuGs5HU5bORorIqU+zuhV3kCj7XcZTIYJepWvK2OoIHORH6YkuISpuQUn1ekY11Da/wiZDdLaz7HhKFmkXdV81C13MnsCj8+81akZSupB2QNBbIW3KOVVlfB9zK+3uFoZCqn68C1iZB0u0BCNFF3/N3mNN3tXTmCtSBD+W8+yxowYM4t9ifX19m6sV1mzrGq0/wsXR5jbizAFe79vONuSS7GnG05SO+ZQe/zg9vh3upuQ9P/nmMvRzpTzXJJ9IliNQCboSpIOsbi5IcNqi1G8EVeKNd3IcK3Z5v8nvEpRqn5vD1MU3FNshrHRPxBNZfz3/6NWjlS+af01Q2Y1NYRtuGJNtIUXjHeexkVZycgI7feXs3XtG+yjJU6Z1SnXj5HJbMneCiQbg/aTMsHG57m8+t5QdA+Zx1Gkz3uxpl92/qzZIWV7JwXbR0l2GmSM8I397zT8e2rlIWmQ8XUESjXdmWWBMR2Oqri8drfzHzvfVG5W2D8lcmyHW2yLJ/JBQNK1CJH+NAwJaOmK5sQK8sSLtp8axcveKN+3Dn1kWDrJqk5O90CfUCAwEAAQ==";

        // 1. 세션 생성기를 사전에 생성해 주세요.
        TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator(publicKeyString);

        // 2. 인증 요청(개인정보가 포함된 경우) API 호출 전에, 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate(AESAlgorithm.AES_CBC);

        // 3. 개인정보를 암호화 해주세요.
        String userName = "소중한 개인정보 입니다";
        String encryptedUserName = tossCertSession.encrypt(userName);
        System.out.println("encryptedUserName: " + encryptedUserName);

        // 4. 요청 파라미터에 추가해주세요.
        String sessionKey = tossCertSession.getSessionKey();
//         String userName = encryptedUserName;

        // 5. 응답을 받은 경우, 요청을 보낼 때 생성했던 tossCertSession 을 가지고 있어야 합니다.
        // encryptedUserName 가 응답을 받은 암호화된 userName 이라고 가정합니다.
        String decryptedUserName = tossCertSession.decrypt(encryptedUserName);

        // 6. decryptedUserName 은 무결성 검증까지 완료되어 있습니다.
        System.out.println("decryptedUserName: " + decryptedUserName);

        Assertions.assertEquals(decryptedUserName, userName);
    }

    @Test
    public void deserializeTest() {
        // 0. 사전에 전달받은 RSA 공개키 입니다.
        String publicKeyString = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAoNsnNuQYnGR2v3XSJc2VNxYSBsykuSLJiOeSbWBSEmNG7Fm48rpzZ5bS2diRjHL55ZWBmtJ2k2WqIcHAQS8VnzhFM+bawDwfk6O8xT6hUr+C6ELENWlk2cJchAuGs5HU5bORorIqU+zuhV3kCj7XcZTIYJepWvK2OoIHORH6YkuISpuQUn1ekY11Da/wiZDdLaz7HhKFmkXdV81C13MnsCj8+81akZSupB2QNBbIW3KOVVlfB9zK+3uFoZCqn68C1iZB0u0BCNFF3/N3mNN3tXTmCtSBD+W8+yxowYM4t9ifX19m6sV1mzrGq0/wsXR5jbizAFe79vONuSS7GnG05SO+ZQe/zg9vh3upuQ9P/nmMvRzpTzXJJ9IliNQCboSpIOsbi5IcNqi1G8EVeKNd3IcK3Z5v8nvEpRqn5vD1MU3FNshrHRPxBNZfz3/6NWjlS+af01Q2Y1NYRtuGJNtIUXjHeexkVZycgI7feXs3XtG+yjJU6Z1SnXj5HJbMneCiQbg/aTMsHG57m8+t5QdA+Zx1Gkz3uxpl92/qzZIWV7JwXbR0l2GmSM8I397zT8e2rlIWmQ8XUESjXdmWWBMR2Oqri8drfzHzvfVG5W2D8lcmyHW2yLJ/JBQNK1CJH+NAwJaOmK5sQK8sSLtp8axcveKN+3Dn1kWDrJqk5O90CfUCAwEAAQ==";

        // 1. 세션 생성기를 사전에 생성해 주세요.
        TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator(publicKeyString);

        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        // 2. DB 혹은 다른 저장소에 저장이 필요한 경우, serialize 를 이용해주세요(민감한 정보이므로 저장시 추가 암호화를 해주세요!!).
        String serialized = tossCertSession.serializeSession();

        // 3. deserialize
        TossCertSession deserializedTossCertSession = tossCertSessionGenerator.deserialize(serialized);

        // 검증
        String plainText = "검증용 문자열";
        String encryptedText = tossCertSession.encrypt(plainText);
        Assertions.assertEquals(tossCertSession.decrypt(encryptedText), deserializedTossCertSession.decrypt(encryptedText));
    }
}
