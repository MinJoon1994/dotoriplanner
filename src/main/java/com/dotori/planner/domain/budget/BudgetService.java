package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import com.dotori.planner.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    private final MemberRepository memberRepository;

    public Optional<Budget> findByMemberAndMonth(Long id, String currentMonth) {

        return budgetRepository.findByMemberIdAndBudgetMonth(id, currentMonth);
    }
    
    //새로운 월 예산 설정
    public void saveBudget(Long id, BudgetSaveDTO dto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보 없음"));

        // 고정지출 총합 계산
        int fixedTotal = dto.getFixedExpenses().stream()
                .mapToInt(BudgetSaveDTO.FixedExpenseDTO::getAmount)
                .sum();

        // ✅ 기타지출 총합 계산 추가
        int etcTotal = dto.getMiscExpenses().stream()
                .mapToInt(BudgetSaveDTO.MiscExpenseDTO::getAmount)
                .sum();

        Budget budget = Budget.builder()
                .member(member)
                .budgetMonth(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .totalBudget(dto.getTotalBudget())
                .etcTotal(etcTotal)
                .fixedTotal(fixedTotal)
                .build();

    }
}
