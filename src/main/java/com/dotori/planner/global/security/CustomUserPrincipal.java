package com.dotori.planner.global.security;

import com.dotori.planner.domain.member.Member;
import com.dotori.planner.domain.member.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class CustomUserPrincipal implements UserDetails, OAuth2User {

    private final Long id;
    private final String username;
    private final String password;
    private final String name;
    private final String email;
    private final String profileImg;
    private final String socialType;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    
    //일반 로그인용 생성자
    public CustomUserPrincipal(Member member) {
        this.id = member.getId();
        this.username = member.getLoginId();
        this.password = member.getPassword();
        this.email = member.getEmail();
        this.profileImg = (member.getProfileImageUrl() != null)
                ? member.getProfileImageUrl()
                : "/images/기본프로필이미지.png";
        this.socialType = member.getSocial_type();
        this.name = member.getName();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        this.attributes = null;
    }
    
    //소셜 로그인용 생성자
    public CustomUserPrincipal(Member member,Map<String,Object> attributes) {
        this.id = member.getId();
        this.username = member.getLoginId();
        this.password = member.getPassword();
        this.email = member.getEmail();
        this.profileImg = (member.getProfileImageUrl() != null)
                ? member.getProfileImageUrl()
                : "/images/기본프로필이미지.png";
        this.socialType = member.getSocial_type();
        this.name = member.getName();
        this.authorities = List.of(new SimpleGrantedAuthority("ROLE_" + member.getRole().name()));
        this.attributes = attributes;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;    // → 이건 네가 원하는대로 Map으로 구현
    }

    @Override
    public String getName() {
        return String.valueOf(name);   // → 보통 user id (PK)로 설정
    }
}
