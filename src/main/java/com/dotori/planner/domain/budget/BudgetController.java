package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import com.dotori.planner.domain.member.MemberService;
import com.dotori.planner.global.security.CustomUserDetailService;
import com.dotori.planner.global.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/budget")
@Log4j2
public class BudgetController {

    private final BudgetService budgetService;
    private final MemberService memberService;

    //월별 예산 메인 화면
    @GetMapping("/month/main")
    public String budgetMain(@AuthenticationPrincipal CustomUserPrincipal user,
                             Model model){

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        Optional<Budget> budgetOptional = budgetService.findByMemberAndMonth(user.getId(), currentMonth);

        if(budgetOptional.isEmpty()){
            return "/month/budgetnew";
        }

        model.addAttribute("budget", budgetOptional.get());

        return "/month/budgetmain";

    }

    //새로운 월 예산 설정
    @PostMapping("/month/new")
    public String budgetNew(@AuthenticationPrincipal CustomUserPrincipal user,
                            BudgetSaveDTO budgetSaveDTO){

        //예산 저장할 멤버 찾아오기
        Member member = memberService.findById(user.getId());

        //멤버 못찾았다면 로그인페이지로 보내기
        if(member == null){
            return "redirect:/member/login";
        }

        //고객이 입력한 데이터로 새로운 예산 생성
        Budget budget = Budget.createBudget(budgetSaveDTO,member);

        //예산 설정
        Budget savedBudget = budgetService.saveBudget(budget);

        //해당월에 해당하는 일일 예산 데이터 생성 (현재 예산생성일부터 말일까지)
        YearMonth yearMonth = YearMonth.parse(budget.getBudgetMonth());
        int daysInMonth = yearMonth.lengthOfMonth(); //해당 월의 마지막 날짜 (28~31)
        long totalBudget = budget.getTotalBudget() - budget.getFixedTotal() - budget.getEtcTotal();
        long recommendedDailyAmount = totalBudget / daysInMonth;

        List<DailyBudget> dailyBudgets = new ArrayList<>();
        for(int day=1; day <= daysInMonth; day++){
            LocalDate date = yearMonth.atDay(day);

            DailyBudget daily = DailyBudget.builder()
                    .member_id(user.getId())
                    .budget(budget)
                    .build();

        }


        return "redirect:/budget/month/main";
    }

    //일일 예산 자세히보기 페이지
    @GetMapping("/daily/{date}")
    public String dailyBudget(@PathVariable("date") String date, Model model){


        return "/daily/dailyBudget";
     }
}
