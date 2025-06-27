package com.dotori.planner.domain.member;

import com.dotori.planner.domain.budget.DailyPointLog;
import com.dotori.planner.domain.budget.Budget;
import com.dotori.planner.domain.budget.DailySpending;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {

    //회원 로그인 관련
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

    //회원 기능 관련
    @Column(nullable = false)
    private int totalPoint = 0; // 도토리 포인트 보유량

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> budgets = new ArrayList<>(); //월 예산 리스트

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailySpending> dailySpendings = new ArrayList<>(); //일일 소비 리스트

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyPointLog> pointLogs = new ArrayList<>(); //포인트 내역

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
        return Member.builder()
                .loginId(memberDTO.getLoginId())
                .password(memberDTO.getPassword())
                .name(memberDTO.getName())
                .email(memberDTO.getEmail())
                .termsAgree(memberDTO.isTermsAgree())
                .privacyAgree(memberDTO.isPrivacyAgree())
                .marketingAgree(memberDTO.isMarketingAgree())
                .role(Role.USER)
                .deleted(false)           // deleted 필드 추가
                .social_type(null)        // 일반 회원가입이므로 null
                .social_id(null)          // 일반 회원가입이므로 null
                .profileImageUrl(null)    // 초기 프로필 이미지 없음
                .build();
    }

}

