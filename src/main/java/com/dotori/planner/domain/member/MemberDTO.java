package com.dotori.planner.domain.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {

    private String loginId;
    private String password;
    private String name;
    private String email;

    private boolean termsAgree;
    private boolean privacyAgree;
    private boolean marketingAgree;
}
