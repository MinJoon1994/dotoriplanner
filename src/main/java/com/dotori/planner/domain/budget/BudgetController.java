package com.dotori.planner.domain.budget;

import com.dotori.planner.global.security.CustomUserDetailService;
import com.dotori.planner.global.security.CustomUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;

    //월별 예산 메인 화면
    @GetMapping("/month/main")
    public String budgetMain(@AuthenticationPrincipal CustomUserPrincipal user){

        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));

        Optional<Budget> budgetOptional = budgetService.findByMemberAndMonth(user.getId(), currentMonth);

        if(budgetOptional.isEmpty()){
            return "/month/budgetnew";
        }

        return "/month/budgetmain";
    }

    //새로운 월 예산 설정
    @PostMapping("/month/new")
    public String budgetNew(@AuthenticationPrincipal CustomUserPrincipal user,
                            BudgetSaveDTO budgetSaveDTO){
        budgetService.saveBudget(user.getId(),budgetSaveDTO);
        return "redirect:/month/main";
    }


}
