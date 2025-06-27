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
@Table(name = "daily_spending", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "spendDate"})
})
public class DailySpending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 어떤 회원의 소비인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // ✅ 소비한 날짜 (ex: 2025-07-04)
    @Column(nullable = false)
    private LocalDate spendDate;

    // ✅ 소비한 금액
    @Column(nullable = false)
    private int amount;

    // ✅ 카테고리 (식비, 교통, 쇼핑 등)
    @Column(nullable = false)
    private String category;

    // ✅ 코멘트 (선택)
    private String comment;

    // ✅ 권장 소비 이하 여부
    @Column(nullable = false)
    private boolean isGoalMet;

    // ✅ 입력 일시 (기록용)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
