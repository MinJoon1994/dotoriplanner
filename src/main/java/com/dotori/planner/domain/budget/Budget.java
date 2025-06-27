package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "budget", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "yearMonth"})
})
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ 회원 연동
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // 예: "2025-07"
    @Column(length = 7, nullable = false)
    private String budgetMonth;

    // 총 예산 금액
    @Column(nullable = false)
    private int totalBudget;

    // 고정 지출 총액
    @Column(nullable = false)
    private long fixedTotal = 0;

    // 기타 지출 총액
    @Column(nullable = false)
    private long etcTotal = 0;

    // ✅ 고정 지출 연동
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedExpense> fixedExpenses = new ArrayList<>();

    // ✅ 기타 지출 연동
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiscExpense> etcExpenses = new ArrayList<>();

    // 생성일
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
