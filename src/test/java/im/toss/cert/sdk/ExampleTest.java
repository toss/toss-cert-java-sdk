package im.toss.cert.sdk;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled // 실행전에 해당라인을 삭제해주세요.
class ExampleTest {
    // 0. 세션 생성기를 사전에 1회만 생성해 주세요.
    TossCertSessionGenerator tossCertSessionGenerator = new TossCertSessionGenerator();

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
        URL url = new URL("https://cert.toss.im/api/v2/sign/user/auth/request");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Authorization", "Bearer eyJraWQiOiJjZXJ0IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ0ZXN0X2E4ZTIzMzM2ZDY3M2NhNzA5MjJiNDg1ZmU4MDZlYjJkIiwiYXVkIjoidGVzdF9hOGUyMzMzNmQ2NzNjYTcwOTIyYjQ4NWZlODA2ZWIyZCIsIm5iZiI6MTY0OTIyMjk3OCwic2NvcGUiOlsiY2EiXSwiaXNzIjoiaHR0cHM6XC9cL2NlcnQudG9zcy5pbSIsImV4cCI6MTY4MDc1ODk3OCwiaWF0IjoxNjQ5MjIyOTc4LCJqdGkiOiI4MDNjNDBjOC1iMzUxLTRmOGItYTIxNC1iNjc5MmNjMzBhYTcifQ.cjDZ0lAXbuf-KAgi3FlG1YGxvgvT3xrOYKDTstfbUz6CoNQgvd9TqI6RmsGZuona9jIP6H12Z1Xb07RIfAVoTK-J9iC5_Yp8ZDdcalsMNj51pPP8wso86rn-mKsrx1J5Rdi3GU58iKt0zGr4KzqSxUJkul9G4rY03KInwvl692HU19kYA9y8uTI4bBX--UPfQ02G0QH9HGTPHs7lZsISDtyD8sB2ikz5p7roua7U467xWy4BnRleCEWO2uUaNNGnwd7SvbjhmsRZqohs9KzDUsFjVhSiRNdHL53XJQ5zFHwDF92inRZFLu6Dw8xttPtNHwAD1kT84uXJcVMfEHtwkQ");
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
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
        JsonObject responseObject = responseReader.readObject();
        System.out.println("\n--------------------------- 인증 요청 결과 -------------------------------------");
        System.out.println("인증 txId: " + responseObject.getJsonObject("success").getString("txId"));
        System.out.println("----------------------------------------------------------------------------\n");
        assertEquals(1, 1);
    }

    // 결과호출을 하기전에 토스앱에서 인증을 완료해 주세요.

    @Test
    void result() throws Exception {
        // 0. 인증 요청 결과에서 응답받은 인증 txId 로 변경한 후 테스트 해주세요.
        String txId = "2122cb6d-46f9-4e72-86eb-3f71c3c97507";

        // 1. 인증 결과 조회 API 호출 전에 세션을 생성해 주세요.
        TossCertSession tossCertSession = tossCertSessionGenerator.generate();

        // 2. 인증요청 API 를 호출해주세요.
        URL url = new URL("https://cert.toss.im/api/v2/sign/user/auth/result");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("Authorization", "Bearer eyJraWQiOiJjZXJ0IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJ0ZXN0X2E4ZTIzMzM2ZDY3M2NhNzA5MjJiNDg1ZmU4MDZlYjJkIiwiYXVkIjoidGVzdF9hOGUyMzMzNmQ2NzNjYTcwOTIyYjQ4NWZlODA2ZWIyZCIsIm5iZiI6MTY0OTIyMjk3OCwic2NvcGUiOlsiY2EiXSwiaXNzIjoiaHR0cHM6XC9cL2NlcnQudG9zcy5pbSIsImV4cCI6MTY4MDc1ODk3OCwiaWF0IjoxNjQ5MjIyOTc4LCJqdGkiOiI4MDNjNDBjOC1iMzUxLTRmOGItYTIxNC1iNjc5MmNjMzBhYTcifQ.cjDZ0lAXbuf-KAgi3FlG1YGxvgvT3xrOYKDTstfbUz6CoNQgvd9TqI6RmsGZuona9jIP6H12Z1Xb07RIfAVoTK-J9iC5_Yp8ZDdcalsMNj51pPP8wso86rn-mKsrx1J5Rdi3GU58iKt0zGr4KzqSxUJkul9G4rY03KInwvl692HU19kYA9y8uTI4bBX--UPfQ02G0QH9HGTPHs7lZsISDtyD8sB2ikz5p7roua7U467xWy4BnRleCEWO2uUaNNGnwd7SvbjhmsRZqohs9KzDUsFjVhSiRNdHL53XJQ5zFHwDF92inRZFLu6Dw8xttPtNHwAD1kT84uXJcVMfEHtwkQ");
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
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
        JsonObject responseObject = responseReader.readObject();

        // 3. 결과를 복호화 합니다.
        String encryptedCi = responseObject.getJsonObject("success").getJsonObject("personalData").getString("ci");
        String ci = tossCertSession.decrypt(encryptedCi);
        System.out.println("\n--------------------------- 인증 결과 조회 CI ----------------------------------");
        System.out.println("복호화 된 CI: " + ci);
        System.out.println("----------------------------------------------------------------------------\n");
        assertEquals(1, 1);
    }
}
