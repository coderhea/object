package org.calling.whenPolicyWithoutFeeRules;

import org.calling.Money;

public interface RatePolicy {
    //now, not Phone but phoneCalls
    Money calculateFee(AnyPhone anyPhone);
}
