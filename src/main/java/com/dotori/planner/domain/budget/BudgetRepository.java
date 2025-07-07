package com.dotori.planner.domain.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    
    //유저해당 월 예산 가져오기
    Optional<Budget> findByMemberIdAndBudgetMonth(Long id, String currentMonth);
    
}
