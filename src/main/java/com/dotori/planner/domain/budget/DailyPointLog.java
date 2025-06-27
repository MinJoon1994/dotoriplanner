package com.dotori.planner.domain.budget;


import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "daily_point_log")
public class DailyPointLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 어떤 회원의 기록인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // ✅ 포인트 발생 날짜 (포인트 기준일)
    @Column(nullable = false)
    private LocalDate spendDate;

    // ✅ 포인트 변화량 (+ or -)
    @Column(nullable = false)
    private int pointAmount;

    // ✅ 포인트 지급 사유
    @Column(length = 30, nullable = false)
    private String pointType;

    // ✅ 로그 기록 시각
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
