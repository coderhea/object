package org.calling.whenPolicyWithoutFeeRules;

import org.calling.FeeRule;
import org.calling.Money;
import org.calling.PhoneCall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// no more abstract. feeCondition only interface changing
public class BasicRatePolicy implements RatePolicy {

    private List<FeeRule> feeRules = new ArrayList<>();

    public BasicRatePolicy(FeeRule ... feeRules) {
        this.feeRules = Arrays.asList(feeRules);
    }

    @Override
    public Money calculateFee(AnyPhone anyPhone) {
        return anyPhone.getCalls()
                .stream()
                .map(phoneCall -> calculate(phoneCall))
                .reduce(Money.ZERO, (first,second) -> first.plus(second));
    }

    private Money calculate(PhoneCall call) {
        return feeRules
                .stream()
                .map(rule -> rule.calculateFee(call))
                .reduce(Money.ZERO, (first, second) -> first.plus(second));
    }


//    @Override
//    public Money calculateFee(AnyPhone anyPhone) {
////        Abstract Phone에서 코드 차용
////        public Money calculateFee() {
//            Money result = Money.ZERO;
//            //List<PhoneCall> calls가 아니라, Phone 인자의 List<> 가져오기
//            for (PhoneCall call : anyPhone.getCalls()) {
//                result.plus(calculateCallFee(call));
//            }
//            return result;
//        }
//    //역시 plus안의 calculateCallFee 컴파일 오류니 abstract method 선언
//    protected abstract Money calculateCallFee(PhoneCall call);

}
