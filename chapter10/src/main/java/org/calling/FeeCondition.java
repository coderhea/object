package org.calling;

import org.calling.whenPolicyWithoutFeeRules.DateTimeInterval;

import java.util.List;

public interface FeeCondition {
    List<DateTimeInterval> findTimeIntervals(PhoneCall call);
}
