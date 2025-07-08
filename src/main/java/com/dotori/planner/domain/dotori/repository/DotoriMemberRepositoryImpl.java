package com.dotori.planner.domain.dotori.repository;

import com.dotori.planner.domain.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DotoriMemberRepositoryImpl implements DotoriMemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Integer findPointByUserId(Long userId) {
        QMember member = QMember.member;

        return queryFactory
                .select(member.totalPoint)
                .from(member)
                .where(member.id.eq(userId))
                .fetchOne();
    }
}
