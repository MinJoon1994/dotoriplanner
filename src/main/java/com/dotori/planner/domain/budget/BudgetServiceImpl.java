package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    @Override
    public Optional<Budget> findByMemberAndMonthAndStatus(Member member, String month, BudgetStatus status) {
        return budgetRepository.findByMemberAndMonthAndStatus(member, month, status);
    }

    @Override
    public Budget createBudget(BudgetDTO dto, Member member) {

        int remaining = dto.getTotalBudget() - dto.getFixedBudget() - dto.getEtcBudget();

        Budget budget = Budget.builder()
                .member(member)
                .month(dto.getMonth())
                .totalBudget(dto.getTotalBudget())
                .fixedBudget(dto.getFixedBudget())
                .etcBudget(dto.getEtcBudget())
                .unexpectedBudget(0) // 초기값
                .remainingBudget(remaining)
                .startDate(LocalDate.now())
                .status(BudgetStatus.ACTIVE)
                .build();

        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getBudgetsByMember(Member member) {
        return List.of();
    }
}
