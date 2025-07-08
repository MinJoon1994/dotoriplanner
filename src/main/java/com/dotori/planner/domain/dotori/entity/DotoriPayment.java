package com.dotori.planner.domain.dotori.entity;
import com.dotori.planner.domain.dotori.dto.DotoriPaymentDto;
import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class DotoriPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유 ID (PK)

    private String orderId;           // 주문 ID (프론트에서 생성한 고유값)
    private String paymentKey;        // Toss Payments가 발급한 결제 키
    private String orderName;         // 주문 상품명 (ex. "티셔츠 외 2건")
    private String method;            // 결제 수단 (ex. CARD, MONEY)
    private Long amount;              // 결제 금액 (KRW, 원단위)
    private String status;            // 결제 상태 (DONE, CANCELED 등)
    private LocalDateTime approvedAt; // 결제 승인 시각
    private String cardCompany;

    //  회원 연관 관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static DotoriPayment create(DotoriPaymentDto dto, Member member) {
        DotoriPayment p = new DotoriPayment();
        p.orderId = dto.getOrderId();
        p.paymentKey = dto.getPaymentKey();
        p.orderName = dto.getOrderName();
        p.method = dto.getMethod();
        p.amount = dto.getAmount();
        p.status = dto.getStatus();
        p.approvedAt = dto.getApprovedAt();
        p.cardCompany = dto.getCardCompany();
        p.member = member;
        return p;
    }
}
