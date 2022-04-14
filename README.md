# toss-cert-java-sdk
JAVA 1.8버전 사용자들을 위한 세션키 발급 및 개인정보 암복호화 SDK 입니다.

## 설치
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
	    <version>0.0.1</version>
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
    implementation("com.github.toss:toss-cert-java-sdk:0.0.1")
}

```

## 예제
[TossCertSessionTest.java](https://github.com/toss/toss-cert-java-sdk/blob/main/src/test/java/im/toss/cert/sdk/TossCertSessionTest.java) 를 참조해주세요.
