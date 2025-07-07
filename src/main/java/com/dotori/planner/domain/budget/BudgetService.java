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
    
    //유저 해당월 예산 가져오기
    public Optional<Budget> findByMemberAndMonth(Long id, String currentMonth) {

        return budgetRepository.findByMemberIdAndBudgetMonth(id, currentMonth);
    }
    
    //새로운 월 예산 설정
    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }


}
