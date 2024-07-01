# toss-cert-java-sdk
JAVA 1.8버전 사용자들을 위한 세션키 발급 및 개인정보 암복호화 SDK 입니다.

## 설치
{version} 에는 [최신버전](https://github.com/toss/toss-cert-java-sdk/releases) 을 명시해주세요.

예시)
```
<version>0.0.14</version>
```

pom.xml 을 사용하시면 아래와 같이 추가해주세요.

```xml
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```

```xml
<dependencies>
	<dependency>
	    <groupId>com.github.toss</groupId>
	    <artifactId>toss-cert-java-sdk</artifactId>
	    <version>{version}</version>
	</dependency>
</dependencies>
```

build.gradle 을 사용하신다면, 아래와 같이 추가해주세요.

```
repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.toss:toss-cert-java-sdk:{version}")
}

```

## 예제
[ExampleTest.java](https://github.com/toss/toss-cert-java-sdk/blob/main/src/test/java/im/toss/cert/sdk/ExampleTest.java) 를 참조해주세요.

## JDK 1.8 미만 버전 사용하는 경우
SDK 내부에서 기본적으로 AES GCM 알고리즘을 사용합니다.

JDK 1.8 미만 버전은 AES GCM 을 지원하지 않으므로 AES CBC 알고리즘을 사용해야합니다.

세션을 생성할 때, 아래와 같이 알고리즘 파라미터를 추가해주세요.
```
TossCertSession tossCertSession = tossCertSessionGenerator.generate(AESAlgorithm.AES_CBC);
```

Oracle JDK 7u171 미만 버전에서 AES-256 알고리즘 이용하려면 [링크](https://www.oracle.com/java/technologies/javase-jce-all-downloads.html)에 설명된 Unlimited Strength Jurisdiction Policy 적용이 필요합니다.

AES-128을 사용하시고 싶으신 경우, 아래 메서드를 사용해주세요.
```
TossCertSession tossCertSession = tossCertSessionGenerator.generateCBC128();
```