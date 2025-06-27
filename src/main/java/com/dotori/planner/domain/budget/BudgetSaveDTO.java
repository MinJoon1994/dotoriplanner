package com.dotori.planner.domain.budget;

import lombok.Data;

import java.util.List;

@Data
public class BudgetSaveDTO {

    private int totalBudget;
    private int etcBudget;

    private List<FixedExpenseDTO> fixedExpenses;
    private List<MiscExpenseDTO> miscExpenses; // ✅ 기타 지출 추가

    @Data
    public static class FixedExpenseDTO {
        private String name;
        private String category;
        private int amount;
    }

    @Data
    public static class MiscExpenseDTO {
        private String name;
        private int amount;
        private String spendDate; // yyyy-MM-dd 형식으로 받음 (LocalDate로 변환 필요)
    }
}
