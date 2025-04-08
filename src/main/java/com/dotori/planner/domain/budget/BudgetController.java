package com.dotori.planner.domain.budget;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BudgetController {

    @GetMapping("/budget")
    public String budgetMain(){return "/Month/budgetmain";}
}
