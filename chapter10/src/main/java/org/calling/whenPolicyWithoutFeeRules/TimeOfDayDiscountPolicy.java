package org.calling.whenPolicyWithoutFeeRules;

import org.calling.BasicRatePolicy;
import org.calling.DateTimeInterval;
import org.calling.Money;
import org.calling.PhoneCall;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeOfDayDiscountPolicy extends BasicRatePolicy {
    private List<LocalTime> starts = new ArrayList<>();
    private List<LocalTime> ends = new ArrayList<>();
    private List<Duration> durations = new ArrayList<>();
    private List<Money> amounts = new ArrayList<>();


    //@Override
    protected Money calculateCallFee(PhoneCall call) {
        Money result = Money.ZERO;
        for (DateTimeInterval interval : call.splitByDay()) {
            for (int loop = 0; loop < starts.size() ; loop++) {
                result.plus(amounts.get(loop)
                        .times(Duration.between(from(interval, starts.get(loop)),
                                to(interval, ends.get(loop))).getSeconds()
                                / durations.get(loop).getSeconds()));
            }
        }
        return result;
    }

    private LocalTime to(DateTimeInterval interval, LocalTime to) {
        return interval.getTo().toLocalTime().isAfter(to) ?
                to : interval.getTo().toLocalTime();
    }

    private LocalTime from(DateTimeInterval interval, LocalTime from) {
        return interval.getFrom().toLocalTime().isBefore(from) ?
                from : interval.getFrom().toLocalTime();
    }
}
