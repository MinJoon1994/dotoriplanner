package com.dotori.planner.domain.dotori.repository;

import com.dotori.planner.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DotoriMemberRepository extends JpaRepository<Member, Long>, DotoriMemberRepositoryCustom {
}
