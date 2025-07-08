package com.dotori.planner.domain.dotori.service;

import com.dotori.planner.domain.dotori.dto.DotoriPaymentDto;
import com.dotori.planner.domain.dotori.entity.DotoriPayment;
import com.dotori.planner.domain.dotori.repository.DotoriPaymentRepository;
import com.dotori.planner.domain.member.Member;
import com.dotori.planner.domain.member.MemberRepository;
import com.dotori.planner.global.security.CustomUserPrincipal;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DotoriPaymentService {
    private final DotoriPaymentRepository dotoriPaymentRepository;
    private final MemberRepository memberRepository;

    private final ObjectMapper objectMapper;

    // 도토리 결제
    @Transactional
    public DotoriPayment savePayment(JsonNode tossResponse, CustomUserPrincipal user) {
        // DTO로 변환
        DotoriPaymentDto dto = DotoriPaymentDto.builder()
                .orderId(tossResponse.get("orderId").asText())
                .paymentKey(tossResponse.get("paymentKey").asText())
                .orderName(tossResponse.get("orderName").asText())
                .method(tossResponse.get("method").asText())
                .amount(tossResponse.get("totalAmount").asLong())
                .status(tossResponse.get("status").asText())
                .approvedAt(LocalDateTime.parse(tossResponse.get("approvedAt").asText().substring(0, 19)))
                .cardCompany(extractCardCompany(tossResponse))
                .build();

        // 영속성 관리되는 Member 가져오기
        Member member = memberRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 정보가 없습니다."));

        // 1. 포인트 충전
        int rechargeAmount = extractDotoriCount(tossResponse.get("orderName").asText());
        member.addPoints(rechargeAmount);

        // 2. 결제
        DotoriPayment payment = DotoriPayment.create(dto, member); //
        return dotoriPaymentRepository.save(payment);
    }
    // 카드가 아닌 다른 결제 수단시 null 대비
    private String extractCardCompany(JsonNode tossResponse) {
        if (tossResponse.has("card") && tossResponse.get("card").has("company")) {
            return tossResponse.get("card").get("company").asText();
        } else if (tossResponse.has("easyPay") && tossResponse.get("easyPay").has("provider")) {
            return tossResponse.get("easyPay").get("provider").asText();
        } else {
            return "UNKNOWN";
        }
    }
    // 숫자만 추출
    private int extractDotoriCount(String orderName) {
        return Integer.parseInt(orderName.replaceAll("[^0-9]", ""));
    }


}
