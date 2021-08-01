package org.calling;

import java.util.List;

//append A. calculateFee(list calls)
public interface RatePolicy {
    Money calculateFee(List<PhoneCall> phoneCalls);
}
