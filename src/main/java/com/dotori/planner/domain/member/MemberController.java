package com.dotori.planner.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Log4j2
public class MemberController {

    private final MemberService memberService;
    
    //회원가임 폼 포워딩
    @GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberDTO",new MemberDTO());
        return "/member/memberForm";
    }

    //로그인 아이디 중복
    @GetMapping("/check-loginid")
    public ResponseEntity<String> checkLoginId(@RequestParam String loginId){
        try {
            Member member = Member.builder().loginId(loginId).build();
            memberService.isLoginIdDuplicate(member);
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        }catch(IllegalStateException e){
            log.info("Member checkLoginId에서 발생한 문제입니다.");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    //이메일 중복 체크
    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmail(@RequestParam String email){
        try{
            Member member = Member.builder().email(email).build();
            memberService.isEmailDuplicate(member);
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        }catch(IllegalStateException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //회원가입
    @PostMapping("/register")
    public String register(@RequestBody MemberDTO memberDTO){
        memberService.registerMember(memberDTO);
        return "redirect:/login";
    }
    
    //로그인 화면 불러오기
    @GetMapping("/login")
    public String loginForm(){
        return "/member/login";
    }



}
