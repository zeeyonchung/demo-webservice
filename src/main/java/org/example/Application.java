package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 프로젝트의 메인 클래스
 * @SpringBootApplication   스프링부트 자동 설정, 스프링 bean 읽기/생성 자동 설정
 *                          이 어노테이션이 있는 위치부터 설정을 읽어가므로 이 클래스는 항상 프로젝트 최상단에 있어야 한다.
 */
@EnableJpaAuditing
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 내장 WAS 실행 - 외부에 WAS를 두지 않고 스프링부트로 만들어진 Jar 파일로 실행
        SpringApplication.run(Application.class, args);
    }
}