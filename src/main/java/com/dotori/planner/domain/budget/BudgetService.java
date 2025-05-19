package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface BudgetService {

    Optional<Budget> findByMemberAndMonthAndStatus(Member member, String month, BudgetStatus status);

    Budget createBudget(BudgetDTO dto, Member member);

    List<Budget> getBudgetsByMember(Member member); // 선택사항
}
