package com.dotori.planner.global.security;

import com.dotori.planner.domain.member.Member;
import com.dotori.planner.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        log.info("➡ 로그인 시도: {}", loginId);
        
        //DB에서 loginId로 Member 조회후 있다면 반환
        Member member = memberRepository.findByLoginId(loginId).orElse(null);;
        if (member == null) {
            log.warn("❌ 사용자 없음: {}", loginId);
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }
        
        //커스터마이징한 UserPrincipal 객체를 생성
        return new CustomUserPrincipal(member);
    }
}

