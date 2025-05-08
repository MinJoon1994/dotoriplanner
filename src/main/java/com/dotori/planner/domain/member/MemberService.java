package com.dotori.planner.domain.member;

import org.springframework.stereotype.Service;

import java.util.Optional;

public interface MemberService {

    //회원 등록
    Member registerMember(MemberDTO memberDTO);
    //회원아이디찾기
    Member findByLoginId(String loginId);
    //로그인 아이디 중복 확인
    boolean isLoginIdAvailable(String loginId);
    //이메일 중복 확인
    boolean isEmailAvailable(String email);

}
