package com.dotori.planner.domain.dotori.service;

import com.dotori.planner.domain.dotori.repository.DotoriMemberRepository;
import com.dotori.planner.domain.dotori.repository.DotoriMemberRepositoryCustom;
import com.dotori.planner.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;

@Service
@RequiredArgsConstructor
public class DotoriService {
    private final DotoriMemberRepository dotoriMemberRepositoryCustom;

    // 아이디로 멤버 총 포인트 조회
    public Integer getTotalPoint(Long userId) {
        Integer totalPoint = dotoriMemberRepositoryCustom.findPointByUserId(userId);
        return totalPoint != null ? totalPoint : 0;
    }
    // 모든 멤버 조회
    public Page<Member> getDotoriRanking(Pageable pageable) {
        return dotoriMemberRepositoryCustom.findAll(
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "totalPoint"))
        );
    }
}
