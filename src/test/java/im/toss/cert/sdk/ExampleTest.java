package im.toss.cert.sdk;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled // 실행전에 해당라인을 삭제해주세요.
class ExampleTest {
    // 0. 세션 생성기를 사전에 1회만 생성해 주세요.
    TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator();

    final String accessToken = "eyJraWQiOiJjZXJ0IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ0ZXN0X2E4ZTIzMzM2ZDY3M2NhNzA5MjJiNDg1ZmU4MDZlYjJkIiwiYXVkIjoidGVzdF9hOGUyMzMzNmQ2NzNjYTcwOTIyYjQ4NWZlODA2ZWIyZCIsIm5iZiI6MTcwOTAyMDA5OSwic2NvcGUiOlsiY2EiXSwiaXNzIjoiaHR0cHM6Ly9jZXJ0LnRvc3MuaW0iLCJleHAiOjE3NDA1NTYwOTksImlhdCI6MTcwOTAyMDA5OSwianRpIjoiZDc3NmUxZmEtZmNkMy00MDE4LTg2MGMtZDA0NTY0YmUxY2U5In0.hQDc7eeY6-a-0tLfcsAO_Tejbmu_Sd7f80P90NtTy6T4HjEUQNji13sMdkhPeibnonE0E8d4fdsyFy2J2KQFLIqFNjV-jPypjm9XcF2yUBwBfG7Jq7k1BBuigPXTN1NistNpnE24F0nNlMzsGZi72YePIFEayFi_SQN5GUwZ9MZbQenGA9sKct0heqKxQj7wuyELgvT7dCFtZ5EU_C_DDhvgtyauGvD4ubtxj2_-SskAnr54LZhW-cDF-rdsAD9knbhcnscpZKXnGVNlXbQzgrVfWNEYlJeZ9bwagdgYh67VrC8SNBoGPuXsKU4eUV17lh_TwB9M2lPkBJLwgaJVgA";

    @Test
    void request() throws Exception {
        // 1. 개인정보가 포함되어 있는 인증요청 API 호출 전에 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        // 2. 개인정보를 암호화 해주세요. 실제 정보를 넣으시면, 토스앱으로 푸시가 갑니다.
        String userName = "김토스";
        String encryptedUserName = tossCertSession.encrypt(userName);
        String userPhone = "01011112222";
        String encryptedUserPhone = tossCertSession.encrypt(userPhone);
        String userBirthday = "20230101";
        String encryptedUserBirthday = tossCertSession.encrypt(userBirthday);

        // 3. 인증요청 API 를 호출해주세요.
        /* json
        {
            "requestType": "USER_PERSONAL",
            "triggerType": "PUSH",
            "sessionKey": tossCertSession.getSessionKey(),
            "userName": encryptedUserName,
            "userPhone": encryptedUserPhone,
            "userBirthday": encryptedUserBirthday
        }
        */
        String requestBody = Json.createObjectBuilder()
            .add("requestType", "USER_PERSONAL")
            .add("triggerType", "PUSH")
            // 3.1 세션키를 넣어주세요.
            .add("sessionKey", tossCertSession.getSessionKey())
            // 3.2 tossCertSession 로 암호화된 개인정보를 넣어주세요.
            .add("userName", encryptedUserName)
            .add("userPhone", encryptedUserPhone)
            .add("userBirthday", encryptedUserBirthday)
            .build()
            .toString();

        JsonObject responseObject = postUrl("https://cert.toss.im/api/v2/sign/user/auth/request", requestBody);

        System.out.println("\n--------------------------- 인증 요청 결과 -------------------------------------");
        System.out.println("인증 txId: " + responseObject.getJsonObject("success").getString("txId"));
        System.out.println("----------------------------------------------------------------------------\n");
    }

    // 결과호출을 하기전에 토스앱에서 인증을 완료해 주세요.

    @Test
    void result() throws Exception {
        // 0. 인증 요청 결과에서 응답받은 인증 txId 로 변경한 후 테스트 해주세요.
        String txId = "인증 txId";

        // 1. 인증 결과 조회 API 호출 전에 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        // 2. 인증요청 API 를 호출해주세요.
        /* json
        {
            "sessionKey": tossCertSession.getSessionKey(),
            "txId": txId
        }
        */
        String requestBody = Json.createObjectBuilder()
            // 2.1 세션키를 넣어주세요.
            .add("sessionKey", tossCertSession.getSessionKey())
            // 2.2 인증 요청 결과의 txId 를 넣어주세요.
            .add("txId", txId)
            .build()
            .toString();

        JsonObject responseObject = postUrl("https://cert.toss.im/api/v2/sign/user/auth/result", requestBody);

        // 3. 결과를 복호화 합니다.
        String encryptedCi = responseObject.getJsonObject("success").getJsonObject("personalData").getString("ci");
        String ci = tossCertSession.decrypt(encryptedCi);
        System.out.println("\n--------------------------- 인증 결과 조회 CI ----------------------------------");
        System.out.println("복호화 된 CI: " + ci);
        System.out.println("----------------------------------------------------------------------------\n");

        // 4. 인증서 유효성을 검사합니다.
        String signature = responseObject.getJsonObject("success").getString("signature");
        String pemCertificate = PKCS7CertificateExtractor.extractCertificate(signature);

        /* json
        {
            "certificate": pemCertificate
        }
        */
        requestBody = Json.createObjectBuilder()
            // 2.1 세션키를 넣어주세요.
            .add("certificate", pemCertificate)
            // 2.2 인증 요청 결과의 txId 를 넣어주세요.
            .build()
            .toString();

        responseObject = postUrl("https://cert.toss.im/api/v1/certificate/validate", requestBody);

        assertTrue(responseObject.getJsonObject("success").getBoolean("valid"));
        assertTrue(responseObject.getJsonObject("success").getBoolean("enabled"));
    }

    private JsonObject postUrl(String urlString, String requestBody) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Authorization", "Bearer " + accessToken);
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

        writer.write(requestBody);
        writer.flush();
        writer.close();

        httpConn.getOutputStream().close();
        InputStream responseStream = httpConn.getResponseCode() == 200
            ? httpConn.getInputStream()
            : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";

        JsonReader responseReader = Json.createReader(new StringReader(response));
        return responseReader.readObject();
    }
}
