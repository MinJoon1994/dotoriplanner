package com.dotori.planner.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dotori/member")
@Log4j2
public class MemberController {

    private final MemberService memberService;
    
    //회원가임 폼 포워딩
    @GetMapping("/new")
    public String memberForm(Model model){
        model.addAttribute("memberDTO",new MemberDTO());
        return "/member/memberForm";
    }

}
