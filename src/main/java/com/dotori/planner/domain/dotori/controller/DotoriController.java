package com.dotori.planner.domain.dotori.controller;

import com.dotori.planner.domain.dotori.service.DotoriPaymentService;
import com.dotori.planner.domain.dotori.service.DotoriService;
import com.dotori.planner.domain.member.Member;
import com.dotori.planner.global.security.CustomUserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dotori")
public class DotoriController {
    private final ObjectMapper objectMapper;
    private final DotoriService dotoriService;

    // 내 도토리
    @GetMapping("/myDotori")
    public String myDotoriPage(@AuthenticationPrincipal CustomUserPrincipal user, Model model) {
        Long userId = user.getId();
        int totalPoint = dotoriService.getTotalPoint(userId);

        model.addAttribute("totalPoint", totalPoint);

        return "dotori/myDotoriPage/myDotoriPage";
    }

    // 도토리 구매 화면 요청
    @GetMapping("/purchase")
    public String purchasePage(Model model) {
        List<Map<String, Object>> items = new ArrayList<>();

        items.add(Map.of("count", 100, "price", 1000));
        items.add(Map.of("count", 500, "price", 5000));
        items.add(Map.of("count", 1000, "price", 10000));
        items.add(Map.of("count", 3000, "price", 30000));
        items.add(Map.of("count", 5000, "price", 50000));
        items.add(Map.of("count", 10000, "price", 1000000));

        model.addAttribute("items", items);
        return "dotori/dotoriPayment/dotoriPurchase";
    }
    // 결제 화면 요청
    @PostMapping("/payment")
    public String renderSuccessPage(@AuthenticationPrincipal CustomUserPrincipal user, Model model,
                                    @RequestParam("dotoriOption") int count,
                                    @RequestParam("price") int price) {
        System.out.println("회원 ID : " + user.getMember().getId());
        // 로그인한 사용자 이름 출력
        System.out.println("로그인한 사용자 이름 : " + user.getMember().getName());
        // 사용자 이메일 출력
        System.out.println("이메일 : " + user.getMember().getEmail());
        // 사용자 역할 확인
        System.out.println("역할 : " + user.getMember().getRole());

        model.addAttribute("member", user.getMember());
        System.out.println("유저 객체 : " + user.getMember());
        model.addAttribute("count", count);
        model.addAttribute("price", price);

        return "dotori/dotoriPayment/dotoriPayment";
    }

    // 도토리 랭킹
    @GetMapping("/dotoriRanking")
    public String getDotoriRanking(Model model, Pageable pageable) {
        Page<Member> page = dotoriService.getDotoriRanking(pageable);
        model.addAttribute("page", page);
        model.addAttribute("members", page.getContent());
        return "dotori/dotoriRanking/dotoriRankingPage";
    }
    // 도토리 상점
    @GetMapping("/dotoriShop")
    public String dotoriShopPage() {
        return "dotori/dotoriShop/dotoriShopMain";
    }

    @PostMapping("/shopping")
    public String handleShoppingPost(@RequestParam(required = false) String avatar,
                                     @RequestParam(required = false) String background,
                                     Model model) {
        model.addAttribute("avatar", avatar);
        model.addAttribute("background", background);
        return "dotori/dotoriShop/shoppedItemPage";
    }
}
