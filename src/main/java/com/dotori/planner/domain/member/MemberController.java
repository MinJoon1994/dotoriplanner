package com.dotori.planner.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @PostMapping("/check-loginId")
    public ResponseEntity<Map<String, Boolean>> checkLoginId(@RequestBody DuplicateCheckRequest request) {
        boolean isAvailable = memberService.isLoginIdAvailable(request.getLoginId());
        log.info("isAvailable:"+isAvailable);
        Map<String, Boolean> response = Map.of("available", isAvailable);
        return ResponseEntity.ok(response);
    }
    
    //이메일 중복
    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody DuplicateCheckRequest request) {
        boolean isAvailable = memberService.isEmailAvailable(request.getEmail());
        log.info("isAvailable:"+isAvailable);
        Map<String, Boolean> response = Map.of("available", isAvailable);
        return ResponseEntity.ok(response);
    }


    //회원가입
    @PostMapping("/register")
    public String register(@ModelAttribute MemberDTO memberDTO){

        log.info("memberDTO:"+memberDTO);

        memberService.registerMember(memberDTO);
        return "redirect:/member/login";
    }
    
    //로그인 화면 불러오기
    @GetMapping("/login")
    public String loginForm(){
        return "/member/loginForm";
    }


    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "/member/loginForm"; // 로그인 뷰 그대로
    }
}

@Getter
@NoArgsConstructor
class DuplicateCheckRequest {
    private String loginId;
    private String email;
}
