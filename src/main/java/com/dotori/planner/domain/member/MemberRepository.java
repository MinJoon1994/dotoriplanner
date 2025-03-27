package com.dotori.planner.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByLoginId(String loginId);
    Member findByEmail(String email);
    boolean existByLoginId(String loginId);
    boolean existByEmail(String email);
}
