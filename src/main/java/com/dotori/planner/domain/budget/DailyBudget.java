package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long member_id; // 연동된 회원 예산 ID

    @ManyToOne
    private Budget budget;

    @Column(nullable = false)
    private LocalDateTime savedDate; //일일 예산 날짜

    @OneToMany(mappedBy = "dailyBudget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailySpending> spendingList; //소비 리스트

    private int totalSpent; //해당 날짜 소비 총액

    private int recommendedSpent; //해당날짜 기준 일일 권장 소비

    private boolean isSuccess; //일일 권장 소비 목표 달성여부

    private int savedAmount;//절약 금액
}
