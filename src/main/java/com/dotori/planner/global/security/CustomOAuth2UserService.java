package com.dotori.planner.global.security;

import com.dotori.planner.domain.member.Member;
import com.dotori.planner.domain.member.MemberRepository;
import com.dotori.planner.domain.member.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String,Object> attributes = oAuth2User.getAttributes();
        Map<String,Object> kakaoAccount = (Map<String,Object>) attributes.get("kakao_account");
        Map<String,Object> profile = (Map<String,Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String name = (String) profile.get("nickname");
        String profileImg = (String) profile.get("profile_image_url");

        log.info("✅ 카카오 로그인 성공 : email={}, nickname={}", email, name);

        Member member = memberRepository.findByLoginId(email)
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .loginId(email)
                        .name(name)
                        .email(email)
                        .deleted(false)
                        .password(passwordEncoder.encode("social_login"))
                        .profileImageUrl(profileImg)
                        .social_id(attributes.get("id").toString())
                        .social_type("KAKAO")
                        .role(Role.USER)
                        .marketingAgree(false)
                        .privacyAgree(false)
                        .termsAgree(false)
                        .build()));

        //SecurityContext에 넣을 CustomUserPrincipal 생성
        return new CustomUserPrincipal(member,attributes);
    }
}
