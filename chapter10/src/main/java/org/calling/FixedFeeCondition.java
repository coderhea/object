package org.calling;

import org.calling.whenPolicyWithoutFeeRules.DateTimeInterval;

import java.util.Arrays;
import java.util.List;

public class FixedFeeCondition implements FeeCondition {
    @Override
    public List<DateTimeInterval> findTimeIntervals(PhoneCall call) {
        return Arrays.asList(call.getInterval());
    }
}
