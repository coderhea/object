package org.calling;

import java.util.List;

public abstract class AdditionalRatePolicy implements RatePolicy {

    private RatePolicy next;

    public AdditionalRatePolicy(RatePolicy next) {
        changeNext(next);
    }

    protected void changeNext(RatePolicy next) {
        this.next = next;
        //불변식
        assert next != null;
    }

    @Override
    public Money calculateFee(List<PhoneCall> phoneCalls) {
        ///불변식 (항상)
        assert next != null;

        //사전 조건
        assert phoneCalls != null;

        Money fee = next.calculateFee(phoneCalls);
        Money result = afterCalculated(fee);

        // 사후 조건
        assert result.isGreaterThanOrEqual(Money.ZERO);

        ///불변식 (항상)
        assert next != null;
        
        return result;
    }

    protected abstract Money afterCalculated(Money fee);
}
