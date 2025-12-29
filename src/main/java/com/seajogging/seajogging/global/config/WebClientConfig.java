package com.seajogging.seajogging.global.config; // 본인의 실제 폴더 경로와 일치해야 함

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient aiWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:5000") // 나중에 파이썬 서버 주소로 변경
                .build();
    }
}