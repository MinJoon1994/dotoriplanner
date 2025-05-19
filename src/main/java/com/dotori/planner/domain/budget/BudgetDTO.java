package com.dotori.planner.domain.budget;

import lombok.Data;

@Data
public class BudgetDTO  {
    private String month;        // "2024-05"
    private int totalBudget;
    private int fixedBudget;
    private int etcBudget;
}
