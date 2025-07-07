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

        // 비밀번호 암호화 추가
        memberDTO.setPassword(passwordEncoder.encode(memberDTO.getPassword()));

        Member member = Member.createMember(memberDTO);

        return memberRepository.save(member);
    }

    //로그인 아이디 중복 체크 서비스
    public boolean isLoginIdAvailable(String loginId) {
        return !memberRepository.existsByLoginId(loginId);
    }

    //이메일 중복 체크 서비스
    public boolean isEmailAvailable(String email) {
        return !memberRepository.existsByEmail(email);
    }

    @Override
    public Member findById(Long id){
        return memberRepository.findById(id).orElse(null);
    };

    @Override
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).orElse(null);
    }
}
