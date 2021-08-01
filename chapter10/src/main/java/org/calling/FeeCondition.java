package org.calling;

import java.util.List;

public interface FeeCondition {
    List<DateTimeInterval> findTimeIntervals(PhoneCall call);
}
