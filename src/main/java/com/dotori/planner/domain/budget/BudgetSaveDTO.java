package com.dotori.planner.domain.budget;

import lombok.Data;

import java.util.List;

@Data
public class BudgetSaveDTO {

    private String budgetMonth;
    private int totalBudget;

    private List<FixedExpenseDTO> fixedExpenses;
    private List<MiscExpenseDTO> miscExpenses; // ✅ 기타 지출 추가

}
