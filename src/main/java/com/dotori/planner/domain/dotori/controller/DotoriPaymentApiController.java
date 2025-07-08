package com.dotori.planner.domain.dotori.controller;

import com.dotori.planner.domain.dotori.entity.DotoriPayment;
import com.dotori.planner.domain.dotori.service.DotoriPaymentService;
import com.dotori.planner.global.security.CustomUserPrincipal;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * ✅ Toss Payments 결제 승인 API를 호출하는 Controller
 * 결제 성공 시, 프론트에서 paymentKey, orderId, amount를 전달받아
 * Toss 서버로 결제 승인 요청을 보낸다.
 */
@Controller
@RequiredArgsConstructor
@PropertySource("classpath:application_payApi.properties")
@RequestMapping("/dotoriPayment")
public class DotoriPaymentApiController {

    // application.properties에 정의된 시크릿 키 (예: test_sk_xxx...)
    @Value("${toss.secretKey}")
    private String tossSecretKey;

    private final DotoriPaymentService paymentService;  // 결제 저장 서비스
    private final ObjectMapper objectMapper;      // JSON 파싱용
    private final RestTemplate restTemplate;

    // 결제 성공 화면 요청
    @GetMapping("/success")
    public String renderSuccessPage() {
        return "dotori/dotoriPayment/success"; // templates/success.html
    }
    // 결제 실패 화면 요청
    @GetMapping("/fail")
    public String renderFailPage() {
        return "dotori/dotoriPayment/fail"; // templates/fail.html
    }

    /**
     * ✅ 결제 승인 API 호출
     * 클라이언트로부터 paymentKey, orderId, amount 를 전달받아
     * Toss Payments 에 결제 승인 요청을 보낸다.
     */
    @ResponseBody
    @PostMapping("/confirm") // 프론트엔드에서 결제 성공 후 호출하는 API 엔드포인트
    public ResponseEntity<?> confirm(@AuthenticationPrincipal CustomUserPrincipal user,
                                     @RequestBody Map<String, Object> payload) {

        // [1] 프론트에서 전달한 결제 관련 데이터 추출
        String paymentKey = (String) payload.get("paymentKey");       // Toss 결제 고유 키
        String orderId = (String) payload.get("orderId");             // 주문 ID
        Long amount = Long.valueOf(payload.get("amount").toString()); // 결제 금액

        // [2] Toss API 호출을 위한 인증 헤더 생성 (secretKey 를 Base64 인코딩)
        /*
            Toss Payments 의 결제 승인 API 는 Basic 인증 방식을 사용한다.

            🔐 Basic 인증이란?
            인증 정보를 ID:Password 형태로 만든 후 이를 Base64로 인코딩한 문자열을
            Authorization: Basic <인코딩된 값> 헤더에 담아서 서버에 전달하는 방식
         */
        String encodedAuth = Base64.getEncoder().encodeToString((tossSecretKey + ":").getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);            // JSON 전송
        headers.set("Authorization", "Basic " + encodedAuth);          // 인증 헤더 추가

        // [3] Toss Payments 결제 승인 API 에 보낼 요청 바디 구성
        Map<String, Object> body = Map.of(
                "paymentKey", paymentKey,
                "orderId", orderId,
                "amount", amount
        );

        try {
            // [4] Toss 결제 승인 API 요청 전송
            // restTemplate 은 외부 API 와 HTTP 통신을 해주는 객체
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.tosspayments.com/v1/payments/confirm",  // ① 요청 보낼 URL
                    new HttpEntity<>(body, headers),                         // ② 요청 본문 + 헤더
                    String.class                                             // ③ 응답 받을 데이터 타입
            );

            // [5] 응답 JSON 문자열을 파싱하여 JsonNode 로 변환
            JsonNode tossResponse = objectMapper.readTree(response.getBody());
            System.out.println("💳 Toss 응답: " + tossResponse.toPrettyString());
            System.out.println(
                    Base64.getEncoder().encodeToString(
                            tossResponse.get("orderName").asText().getBytes(StandardCharsets.UTF_8)
                    )
            );

            // [6] 응답 데이터를 기반으로 결제 정보를 DB에 저장
            DotoriPayment savedPayment = paymentService.savePayment(tossResponse, user);

            // [7] 프론트엔드에 Toss 응답을 그대로 전달 (결제 성공 처리)
            return ResponseEntity.ok(tossResponse);

        } catch (Exception e) {
            // [8] 예외 발생 시 에러 메시지와 함께 400 Bad Request 반환
            Map<String, String> error = Map.of(
                    "message", "결제 승인 실패: " + e.getMessage(),
                    "code", "CONFIRM_ERROR"
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}
