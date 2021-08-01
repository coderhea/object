package org.calling;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class TimeOfDayFeeCondition implements FeeCondition {
    private final LocalTime from;
    private final LocalTime to;
    /* TimeOfDayDiscountPolicy had :
       starts, ends, duration, (amount --FeePerDuration)
     */

    public TimeOfDayFeeCondition(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<DateTimeInterval> findTimeIntervals(PhoneCall call) {
        return call.getInterval().splitByDay()
                .stream()
                .filter(each -> from(each).isBefore(to(each)))
                .map(each -> DateTimeInterval.of(
                        LocalDateTime.of(each.getFrom().toLocalDate(), from(each)),
                        LocalDateTime.of(each.getTo().toLocalDate(), to(each))))
                .collect(Collectors.toList());
    }

    //from and to is same as TimeOfDayDiscountPolicy just from,to it has on its own
    private LocalTime from(DateTimeInterval interval) {
        return  interval.getFrom().toLocalTime().isBefore(from) ?
                from : interval.getFrom().toLocalTime();
    }

    private LocalTime to(DateTimeInterval interval) {
        return interval.getFrom().toLocalTime().isBefore(from) ?
                from : interval.getFrom().toLocalTime();
    }
}


/*
 @Override
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


 */