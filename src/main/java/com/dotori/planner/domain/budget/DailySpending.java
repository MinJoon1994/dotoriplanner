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
@Table(name = "daily_spending")
public class DailySpending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 소비한 금액
    @Column(nullable = false)
    private int amount;

    // ✅ 카테고리 (식비, 교통, 쇼핑 등)
    @Column(nullable = false)
    private String category;

    // ✅ 코멘트 (선택)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyBudget dailyBudget;
}
