package com.dotori.planner.domain.member;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //회원 ID(PK)

    @Column(nullable = false, unique = true)
    private String loginId; //회원 로그인 아이디

    @Column(nullable = false)
    private String password; //회원 로그인 비밀번호

    @Column(nullable = false)
    private String name; //회원 이름

    @Column(nullable = false, unique = true)
    private String email; //회원 이메일

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; //회원 역할

    @Column(nullable = false)
    private Boolean termsAgree; //회원 서비스 약관 이용 동의

    @Column(nullable = false)
    private Boolean privacyAgree; //회원 개인정보 접근 권한 동의

    private Boolean marketingAgree; //회원 마케팅 수신 동의

    @Column(nullable = false)
    private Boolean deleted = false; //회원 삭제 여부

    @Column(updatable = false)
    private LocalDateTime createdAt; //회원 가입일

    private LocalDateTime updatedAt; //회원 정보 수정일

    private LocalDateTime lastLoginAt; //회원 마지막 접속일

    private String profileImageUrl; //회원 프로필 이미지 URL
    
    private String social_type; //회원 소셜 로그인 종류
    
    private String social_id; //회원 소셜 로그인 ID


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    //1. dto -> entity
    public static Member createMember(MemberDTO memberDTO){
        Member member =Member.builder()
                .loginId(memberDTO.getLoginId())
                .password(memberDTO.getPassword())
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .termsAgree(memberDTO.isTermsAgree())
                .privacyAgree(memberDTO.isPrivacyAgree())
                .marketingAgree(memberDTO.isMarketingAgree())
                .role(Role.USER) //유저로 설정
                .build();
        return member;
    }
}

