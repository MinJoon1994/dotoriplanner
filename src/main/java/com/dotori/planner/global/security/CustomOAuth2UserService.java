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
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // "kakao", "naver"
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // provider에 따라 사용자 정보 추출
        SocialUserInfo userInfo = extractUserInfo(registrationId, attributes);

        log.info("✅ {} 로그인 성공 : email={}, nickname={}", registrationId.toUpperCase(), userInfo.email(), userInfo.name());

        // 기존 회원 조회 or 신규 회원 등록
        Member member = memberRepository.findByLoginId(userInfo.email())
                .orElseGet(() -> memberRepository.save(Member.builder()
                        .loginId(userInfo.email())
                        .name(userInfo.name())
                        .email(userInfo.email())
                        .deleted(false)
                        .password(passwordEncoder.encode("social_login"))
                        .profileImageUrl(userInfo.profileImg())
                        .social_id(userInfo.socialId())
                        .social_type(registrationId.toUpperCase())
                        .role(Role.USER)
                        .marketingAgree(false)
                        .privacyAgree(false)
                        .termsAgree(false)
                        .build()));

        return new CustomUserPrincipal(member, attributes);
    }

    /**
     * provider 별로 사용자 정보 추출
     */
    private SocialUserInfo extractUserInfo(String registrationId, Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

            return new SocialUserInfo(
                    kakaoAccount.get("email").toString(),
                    profile.get("nickname").toString(),
                    profile.get("profile_image_url") != null ? profile.get("profile_image_url").toString() : null,
                    attributes.get("id").toString()
            );
        } else if ("naver".equals(registrationId)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            return new SocialUserInfo(
                    response.get("email").toString(),
                    response.get("nickname").toString(),
                    response.get("profile_image") != null ? response.get("profile_image").toString() : null,
                    response.get("id").toString()
            );
        } else if ("google".equals(registrationId)) {
            return new SocialUserInfo(
                    attributes.get("email").toString(),
                    attributes.get("name").toString(),
                    attributes.get("picture") != null ? attributes.get("picture").toString() : null,
                    attributes.get("sub").toString()     // google user unique id
            );
        }else {
            throw new OAuth2AuthenticationException("지원하지 않는 소셜 로그인입니다: " + registrationId);
        }
    }

    /**
     * 소셜 사용자 정보를 담을 record (Java 17 이상)
     */
    private record SocialUserInfo(String email, String name, String profileImg, String socialId) {}


}