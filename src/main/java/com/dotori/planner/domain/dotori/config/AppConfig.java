package com.dotori.planner.domain.dotori.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // 기존 메시지 컨버터 제거 (기존 인코딩 문제 방지)
        restTemplate.getMessageConverters().removeIf(converter ->
                converter instanceof StringHttpMessageConverter);

        // 반드시 인덱스 0에 추가해야 JSON 응답 우선 처리
        restTemplate.getMessageConverters().add(0,
                new StringHttpMessageConverter(StandardCharsets.UTF_8));

        return restTemplate;
    }
}
