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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("➡ 로그인 시도: {}", username);

        Member member = memberRepository.findByLoginId(username);
        if (member == null) {
            log.warn("❌ 사용자 없음: {}", username);
            throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
        }

        return new CustomUserPrincipal(
                member.getLoginId(),
                member.getPassword(),
                member.getRole()
        );
    }
}

