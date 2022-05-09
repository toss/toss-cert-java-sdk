package im.toss.cert.sdk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TossCertSessionTest {
    @Test
    public void test() {
        // 1. 세션 생성기를 사전에 생성해 주세요.
        TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator();

        // 2. 인증 요청(개인정보가 포함된 경우) API 호출 전에, 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        // 3. 개인정보를 암호화 해주세요.
        String userName = "소중한 개인정보 입니다";
        String encryptedUserName = tossCertSession.encrypt(userName);
        System.out.println("encryptedUserName: " + encryptedUserName);

        // 4. 요청 파라미터에 추가해주세요.
         String sessionKey = tossCertSession.getSessionKey();
        System.out.println("sessionKey: " + sessionKey);
//         String userName = encryptedUserName;

        // 5. 응답을 받은 경우, 요청을 보낼 때 생성했던 tossCertSession 을 가지고 있어야 합니다.
        // encryptedUserName 가 응답을 받은 암호화된 userName 이라고 가정합니다.
        String decryptedUserName = tossCertSession.decrypt(encryptedUserName);

        // 6. decryptedUserName 은 무결성 검증까지 완료되어 있습니다.
        System.out.println("decryptedUserName: " + decryptedUserName);

        Assertions.assertEquals(decryptedUserName, userName);
    }
}
