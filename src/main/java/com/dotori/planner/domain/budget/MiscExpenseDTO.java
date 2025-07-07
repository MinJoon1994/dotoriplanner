package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MiscExpenseDTO {
    private String name;
    private int amount;
    private String spendDate; // yyyy-MM-dd 형식으로 받음 (LocalDate로 변환 필요)

    public MiscExpense toEntity(Member member, Budget budget) {
        return MiscExpense.builder()
                .member(member)
                .budget(budget)
                .name(this.name)
                .amount(this.amount)
                .spendDate(LocalDate.parse(this.spendDate)) // 문자열을 LocalDate로 변환
                .build();
    }
}
