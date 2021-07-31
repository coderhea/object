package org.calling;

import java.util.ArrayList;
import java.util.List;

public class DayOfWeekDiscountPolicy extends BasicRatePolicy{

    // all policies can switch parameter NO FINAL
    private List<DayOfWeekDiscountRule> rules = new ArrayList<>();
    public DayOfWeekDiscountPolicy(List<DayOfWeekDiscountRule> rules) {
        this.rules = rules;
    }

    //요일별 할인정책 받되, 실제 계산은 역시 날짜/시간대 구분 필요
    @Override
    protected Money calculateCallFee(PhoneCall call) {
        Money result = Money.ZERO;
        for (DateTimeInterval interval : call.getInterval().splitByDay()) {
            for (DayOfWeekDiscountRule rule : rules) {
                result.plus(rule.calculate(interval));
            }
        }
        return result;
    }
}
