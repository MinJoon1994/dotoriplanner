package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {

    Optional<Budget> findByMemberAndMonthAndStatus(Member member, String month, BudgetStatus status);

}
