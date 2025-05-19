package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.budget.BudgetStatus;
import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //예산 ID(PK)

    @Column(nullable = false)
    private String month; // ex) "2024-05" (yyyy-MM)

    @Column(nullable = false)
    private int totalBudget; // 총 예산

    private int fixedBudget; //고정 지출
    private int etcBudget; //기타 지출
    private int unexpectedBudget; //돌발 지출

    private int remainingBudget; // 총 예산 - 고정 - 기타 (일별 예산 배분용)

    private LocalDate startDate; // 예산 적용 시작일 (기본값: LocalDate.now())

    @Enumerated(EnumType.STRING)
    private BudgetStatus status; // ACTIVE, EXPIRED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  //회원(FK)

    @Column(updatable = false)
    private LocalDateTime createdAt;    //생성일

    private LocalDateTime updatedAt;    //수정일

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.startDate = LocalDate.now(); // 기본값 세팅
        this.status = BudgetStatus.ACTIVE;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
