package org.calling;

import java.time.Duration;

public class FixedFeePolicy extends BasicRatePolicy {
    private Money amount;
    private Duration seconds;

    //abstract class no 생성자, no super()
    public FixedFeePolicy(Money amount, Duration seconds) {
        this.amount  = amount;
        this.seconds = seconds;
    }

    //RegularPhone의 calculateCallFee() 코드 차용
    @Override
    protected Money calculateCallFee(PhoneCall call) {
        return amount.times(call.getDuration().getSeconds() / seconds.getSeconds());
    }
}
