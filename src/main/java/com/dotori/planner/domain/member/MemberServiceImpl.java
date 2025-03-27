package com.dotori.planner.domain.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member registerMember(MemberDTO memberDTO) {

        Member member = Member.createMember(memberDTO);
    
        //아이디 중복 검사
        isLoginIdDuplicate(member);
        //이메일 중복 검사
        isEmailDuplicate(member);

        return memberRepository.save(member);
    }

    @Override
    public void isLoginIdDuplicate(Member member) {
        Member findLoginId=memberRepository.findByLoginId(member.getLoginId());
        if(findLoginId!=null) throw new IllegalStateException("이미 중복된 아이디입니다.");
    }

    @Override
    public void isEmailDuplicate(Member member) {
        Member findEmail=memberRepository.findByEmail(member.getEmail());
        if(findEmail!=null) throw new IllegalStateException("이미 가입된 이메일 입니다.");
    }

    @Override
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId);
    }
}
