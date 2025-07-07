package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import lombok.Data;

@Data
public class FixedExpenseDTO {
    private String name;
    private String category;
    private int amount;

    public FixedExpense toEntity(Member member, Budget budget, boolean isRecurring) {
        return FixedExpense.builder()
                .member(member)
                .budget(budget)
                .name(this.name)
                .category(this.category)
                .amount(this.amount)
                .isRecurring(isRecurring)
                .build();
    }
}
