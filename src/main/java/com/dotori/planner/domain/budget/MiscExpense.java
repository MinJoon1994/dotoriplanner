package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "misc_expense")
public class MiscExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 연관된 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // ✅ 연관된 월 예산
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "budget_id", nullable = false)
    private Budget budget;

    // ✅ 지출 항목 이름 (예: 친구 결혼식)
    @Column(nullable = false)
    private String name;

    // ✅ 금액
    @Column(nullable = false)
    private int amount;

    // ✅ 실제 지출 예정일
    @Column(nullable = false)
    private LocalDate spendDate;
}
