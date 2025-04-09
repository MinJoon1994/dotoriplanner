package com.dotori.planner.domain.budget;

import com.dotori.planner.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor@AllArgsConstructor
@Builder
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                //Budget ID
    @Column(nullable = false)
    private String month;           //예산적용 월
    private int total_budget;       //총 예산 금액
    private int etc_budget;         //기타 추가 예산
    private int fixed_budget;       //고정 지출 예산
    private int unexpected_budget;  //돌발 지출 예산

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;          //예산 사용자

}
