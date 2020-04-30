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