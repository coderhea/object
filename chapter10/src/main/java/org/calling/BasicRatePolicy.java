package org.calling;

import java.util.List;

public abstract class BasicRatePolicy implements RatePolicy {
    @Override
    public Money calculateFee(List<PhoneCall> phoneCalls) {
        //사전 조건
        assert phoneCalls != null;

            Money result = Money.ZERO;

            for (PhoneCall phoneCall : phoneCalls) {
                result.plus(calculateCallFee(phoneCall));
            }

        // 사후 조건
        assert result.isGreaterThanOrEqual(Money.ZERO);

            return result;
    }

    protected abstract Money calculateCallFee(PhoneCall phoneCall);
}
