package org.calling;

import org.calling.whenPolicyWithoutFeeRules.DateTimeInterval;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

//now using DateTimeInterval.class
public class PhoneCall {
    // not final
    private DateTimeInterval interval;

    public PhoneCall(LocalDateTime from, LocalDateTime to) {
            this.interval = DateTimeInterval.of(from, to);
    }

    public Duration getDuration() {
            return interval.duration();
    }

    // interval's
    public LocalDateTime getFrom() {
            return interval.getFrom();
    }

    public LocalDateTime getTo() {
        return interval.getTo();
    }

        //getter

    public DateTimeInterval getInterval() {
        return interval;
    }

    public List<DateTimeInterval> splitByDay() {
        return interval.splitByDay();
    }
}
