package im.toss.cert.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TossCertSessionTest {
    @Test
    void test() {
        // 1. 세션 생성기를 사전에 1회만 생성해 주세요.
        TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator();

        // 2. 개인정보가 포함되어 있는 인증요청 API 호출 전에 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        // 3. 개인정보를 암호화 해주세요.
        String userName = "김토스";
        String encryptedUserName = tossCertSession.encrypt(userName);
        System.out.println("encryptedUserName: " + encryptedUserName);

        // 4. 인증요청 API를 호출해 주세요.
        // 인증요청 API의 바디 파라미터에 생성된 sessionKey를 추가해 주세요.
        String sessionKey = tossCertSession.getSessionKey();

        // 5. 사용자의 인증이 끝나면 결과조회 API 호출 전에 새로운 세션을 생성해 주세요.
        TossCertSession tossCertSession2 = tossCertSessionGenerator.generate();

        // 6. 결과조회 API를 호출해주세요.
        // 결과조회 API의 바디 파라미터에 생성된 sessionKey를 추가해 주세요.
        String sessionKey2 = tossCertSession2.getSessionKey();
        String txId = "a39c84d9-458d-47e4-acf7-c481e851f79b";

        // 7. 복호화를 위해 결과조회 요청에서 생성했던 tossCertSession를 가지고 있어야 합니다.
        // response.userName 을 응답받은 암호화된 userName 이라고 가정합니다.
        // decryptedUserName 은 무결성 검증까지 완료되어 있습니다.
        // String decryptedUserName = tossCertSession2.decrypt(response.userName);
    }

    @Test
    void cdcTest() {
        // 1. 세션 생성기를 사전에 1회만 생성해 주세요.
        TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator();

        // 2. 개인정보가 포함되어 있는 인증요청 API 호출 전에 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate(AESAlgorithm.AES_CBC);

        // 3. 개인정보를 암호화 해주세요.
        String userName = "김토스";
        String encryptedUserName = tossCertSession.encrypt(userName);
        System.out.println("encryptedUserName: " + encryptedUserName);

        // 4. 인증요청 API를 호출해 주세요.
        // 인증요청 API의 바디 파라미터에 생성된 sessionKey를 추가해 주세요.
        String sessionKey = tossCertSession.getSessionKey();

        // 5. 사용자의 인증이 끝나면 결과조회 API 호출 전에 새로운 세션을 생성해 주세요.
        TossCertSession tossCertSession2 = tossCertSessionGenerator.generate();

        // 6. 결과조회 API를 호출해주세요.
        // 결과조회 API의 바디 파라미터에 생성된 sessionKey를 추가해 주세요.
        String sessionKey2 = tossCertSession2.getSessionKey();
        String txId = "a39c84d9-458d-47e4-acf7-c481e851f79b";

        // 7. 복호화를 위해 결과조회 요청에서 생성했던 tossCertSession를 가지고 있어야 합니다.
        // response.userName 을 응답받은 암호화된 userName 이라고 가정합니다.
        // decryptedUserName 은 무결성 검증까지 완료되어 있습니다.
        // String decryptedUserName = tossCertSession2.decrypt(response.userName);
    }

    @Test
    void sessionKeyNotIncludeLineBreak() {
        TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator();
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        String sessionKey = tossCertSession.getSessionKey();
        Pattern pattern = Pattern.compile("[\\r\\n]");
        Matcher matcher = pattern.matcher(sessionKey);

        Assertions.assertFalse(matcher.find());
    }
}
