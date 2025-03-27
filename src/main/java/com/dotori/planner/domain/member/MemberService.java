package com.dotori.planner.domain.member;

import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MemberService {

    //로그인 아이디 중복 확인
    void isLoginIdDuplicate(Member member);
    //이메일 중복 확인
    void isEmailDuplicate(Member member);
    //회원 등록
    Member registerMember(MemberDTO memberDTO);
    //회원아이디찾기
    Member findByLoginId(String loginId);
}
