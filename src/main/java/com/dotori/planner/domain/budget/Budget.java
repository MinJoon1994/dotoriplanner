package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    private long fixedTotal;

    // 기타 지출 총액
    @Column(nullable = false)
    private long etcTotal;

    // ✅ 고정 지출 연동
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FixedExpense> fixedExpenses;

    // ✅ 기타 지출 연동
    @OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MiscExpense> etcExpenses;

    // 생성일
    private LocalDateTime createdAt;

    //일일예산
    @OneToMany
    private List<DailyBudget> dailyBudgetList;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // DTO -> Entity
    public static Budget createBudget(BudgetSaveDTO dto,Member member) {

        //1.고정지출 총합 구하기
        long fixedTotal = dto.getFixedExpenses().stream()
                .mapToLong(FixedExpenseDTO::getAmount)
                .sum();

        //2.기타지출 총합 구하기
        long etcTotal = dto.getMiscExpenses().stream()
                .mapToLong(MiscExpenseDTO::getAmount)
                .sum();

        //3.budget을 만들고 변수에 저장
        Budget budget = Budget.builder()
                .member(member)
                .budgetMonth(dto.getBudgetMonth())
                .totalBudget(dto.getTotalBudget())
                .fixedTotal(fixedTotal)
                .etcTotal(etcTotal)
                .build();

        //4.고정지출 담을 배열 생성
        List<FixedExpense> fixedList = new ArrayList<>();
        
        //5.고정지출 DTO -> Entity로 변환해서 리스트에 담기
        for(FixedExpenseDTO fixedExpenseDTO  : dto.getFixedExpenses()){

            fixedList.add(fixedExpenseDTO.toEntity(member,budget,true));
        }
        
        //6.기타지출 담을 배열 생성
        List<MiscExpense> miscList = new ArrayList<>();
        
        //7.기타지출 DTO -> Entity로 변환해서 리스트에 담기
        for(MiscExpenseDTO miscExpenseDTO  : dto.getMiscExpenses()){

            miscList.add(miscExpenseDTO.toEntity(member,budget));
        }

        //8. 자식 리스트를 set
        budget.setFixedExpenses(fixedList);
        budget.setEtcExpenses(miscList);
        
        return budget;
    }
}
