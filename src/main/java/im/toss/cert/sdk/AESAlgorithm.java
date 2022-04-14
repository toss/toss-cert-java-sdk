package im.toss.cert.sdk;

public enum AESAlgorithm {
    AES_CBC("AES/CBC/PKCS5Padding"),
    AES_GCM("AES/GCM/NoPadding"),
    ;

    String algorithmName;

    AESAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}
