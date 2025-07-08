package com.dotori.planner.domain.dotori.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DotoriPaymentDto {
    private String orderId;
    private String paymentKey;
    private String orderName;
    private String method;
    private Long amount;
    private String status;
    private LocalDateTime approvedAt;
    private String cardCompany;
    private Long userId;
    private String userName;
}
