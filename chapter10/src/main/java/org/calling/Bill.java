package org.calling;

// 요금청구서, 계약 RatePolicy 서브타입 구현체 검증용도
public class Bill {
    private Money fee;
    private Phone phone;

    public Bill(Phone phone, Money fee) {
        if (phone == null) {
            throw new IllegalArgumentException();
        }

        if (fee.isLessThan(Money.ZERO)) {
            throw new IllegalArgumentException();
        }

        this.phone = phone;
        this.fee = fee;
    }
}
