package org.calling;

public class FeeRule {
    //역시 바뀔 수 있음 "합성" NO FINAL
    private FeeCondition feeCondition;
    private FeePerDuration feePerDuration;

    public FeeRule(FeeCondition feeCondition, FeePerDuration feePerDuration) {
        this.feeCondition = feeCondition;
        this.feePerDuration = feePerDuration;
    }

    //stream API 복습필요
    public Money calculateFee(PhoneCall call) {
        return feeCondition.findTimeIntervals(call)
                .stream()
                .map(each -> feePerDuration.calculate(each))
                .reduce(Money.ZERO, (first,second) -> first.plus(second));
    }
}
