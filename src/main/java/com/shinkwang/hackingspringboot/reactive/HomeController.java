package com.shinkwang.hackingspringboot.reactive;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class HomeController {

    @GetMapping
    Mono<String> home() {
        return Mono.just("home");
    }
}

/**
 * Mono는 0 또는 1 개의 원소만 담을 수 있는 리액티브 발행자(publisher)로서, 프로젝트 리액터에서 제공해주는 구현체이다.
 * 프로젝트 리액터 개발 초기에 Mono의 필요성에 대한 고민 끝에 하나의 원소만 비동기적으로 반환하는 경우가
 * 압도적으로 많음을 깨닫고 Mono를 추가하기로 했다.
 * 리액티브 스트림은 배압과 지연을 지원한다.
 */