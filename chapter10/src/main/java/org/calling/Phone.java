package org.calling;

import java.util.ArrayList;
import java.util.List;

public class Phone {

    private RatePolicy ratePolicy;
    private List<PhoneCall> phoneCalls = new ArrayList<>();

    public Phone(RatePolicy ratePolicy) {
        this.ratePolicy = ratePolicy;
    }

    public void call(PhoneCall phoneCall) {
        phoneCalls.add(phoneCall);
    }
    // Record성격, new (청구대상 phone, 통화요금 fee)
    public Bill publishBill() {
        return new Bill(this, ratePolicy.calculateFee(phoneCalls));
    }
}
