package com.dotori.planner.domain.dotori.repository;

import com.dotori.planner.domain.dotori.entity.DotoriPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DotoriPaymentRepository extends JpaRepository<DotoriPayment, Long> {
}
