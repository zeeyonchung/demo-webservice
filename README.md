## 프로젝트 시작하기

### Gradle 프로젝트를 스프링 프로젝트로 변경하기
[build.gradle](./build.gradle)
- 자바 개발 기초 설정
```
plugins {
    id 'java'
}

group 'org.example'
version '1.0-SNAPSHOT'

sourceCompatibility = 1.8

repositories { //의존성을 어느 원격 저장소에서 받을지 설정
    mavenCentral()
}

dependencies {
    testCompile group: 'junit', name: 'junit', version: '4.12'
}
```

- 프로젝트의 플러그인 의존성 관리
```
buildscript {
    ext { //build.gradle에서 사용하는 전역변수 설정
        springBootVersion = '2.1.7.RELEASE'
    }
    repositories {
        mavenCentral()
        jcenter() //mavenCentral보다 라이브러리 업로드가 간단한 저장소로 최근에 많이 사용된다
    }
    dependencies { //의존성을 받음
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
    }
}

// 앞서 선언한 플러그인 의존성들을 적용할 것인지 결정
apply plugin: 'java'
apply plugin: 'eclipse'
apply plugin: 'org.springframework.boot'
apply plugin: 'io.spring.dependency-management' //스프링부트의 의존성들을 관리해주는 플러그인
```

- 프로젝트의 의존성 관리
```
repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    compile('org.springframework.boot:spring-boot-starter-web') //버전을 명시하지 않아야 org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}의 버전을 따라간다
    testCompile('org.springframework.boot:spring-boot-starter-test')
}
```


### 스프링 부트 OAuth
- spring-security-oauth2-autoconfigure를 사용하면 스프링부트 2에서도 1.5에서 쓰던 설정을 그대로 사용할 수 있다.
- 스프링부트 2 시큐리티에서는 기본적으로 인증 성공 후 리다이렉트 URL로 `{도메인}/login/oauth2/code/{소셜서비스코드}`를 지원한다.
- 로그인은 `/oauth2/authorization/google`, 로그아웃은 `/logout`
- 도메인
    - [User](src/main/java/org/example/domain/user/User.java)
    : 사용자 정보를 담당할 도메인
    - [Role](src/main/java/org/example/domain/user/Role.java)
    : 사용자의 권한을 관리할 Enum
        - 스프링 시큐리티에서는 권한 코드 앞이 항상 `ROLE_` 이어야 한다.
- 스프링 시큐리티 설정
    - [build.gradle](./build.gradle)
    ```
    compile('org.springframework.boot:spring-boot-starter-oauth2-client')
    ```
    - [application-oauth.properties](src/main/resources/application-oauth-sample.properties) (이 설정파일이 포함되게 설정해줄 것)
    ```
    spring.security.oauth2.client.registration.google.client-id=
    spring.security.oauth2.client.registration.google.client-secret=
    spring.security.oauth2.client.registration.google.scope=profile,email
    ```
    - 소셜 로그인 등 클라이언트 입장에서 소셜 기능 구현시 필요한 의존성
    - spring-security-oauth2-client 와 spring-security-oauth2-jose 를 관리해준다.
    - [SecurityConfig](src/main/java/org/example/config/auth/SecurityConfig.java)
- 세션 데이터베이스에 저장하기
    - 세션은 기본적으로 실행되는 WAS의 메모리에 저장된다.
    - 세션 저장소 선택지
        - 톰캣 세션 사용
            - 2대 이상의 WAS가 구동되는 환경에서는 톰캣들간의 세션 공유 설정 필요
        - 데이터베이스 사용
            - 로그인 요청마다 DB IO 발생
            - 로그인 요청이 많이 없는 사내 시스템 용도로 많이 사용
        - Redis, Memcached 등 메모리 DB 사용
            - 외부 메모리 서버 필요
            
    - [build.gradle](./build.gradle)
    ```
    compile('org.springframework.session:spring-session-jdbc')
    ```
    - [application.properties](src/main/resources/application.properties)
    ```
    spring.session.store-typ=jdbc
    ```
