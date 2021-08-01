## INIT
# A 계약에 의한 설계 

## p537 계약
- 각 계약 당사자는 계약으롭터 이익benefit을 기대하고 이익을 얻기 위해 의무obligation를 이행한다
- 각 계약 당사자의 이익과 의무는 계약서에 public Interface상에 문서화된다
- 사전조건(precondition) : 메서드 호출되기 위해 만족돼야 하는, 요구사항
- 사후조건(postcondition) : 메서드 실행 후 클라이언트에 보장해야 하는 조건 예외 던져야 
- 불변식(invariant) : 항상 '참'이라고 보장되는 서버의 조건. 메서드 실행 전 또는 후 : 꼭 '참'

### 리스코프 치환원칙
클라이언트와 슈퍼타입 간 체결 '계약' 준수 
1. 협력에 참여하는 객체에 대한 기대를 표현하는 '계약 규칙(contract rules)' 
- 서브타입에 더 강력한 사전조건 정의 불가능
    부모클래스 RatePolicy, 서브클래스 BasicRatePolicy
    서브클래스에 phoneCalls가 빈 리스트 안된다 추가? notNullnorEmpty?
    Phone 생성자 즉 통화량 0여도 바로 publishBill() : 여기서 에러발생
    Phone(클라이언트) "BasicRatePolicy왜 RatePolicy 대체못함? 서브타입 아님"
- 서브타입에 더 완화된 사후조건 정의 불가능
    부모클래스 AdditionalRatePolicy, 서브클래스 RateDiscountablePolicy
    부모클래스에 calculateFee결과가 0보다 작아도 된다 (사후조건 완화)
    Bill에서 오류, fee가 0이다 오류 : 원인은 Policy에서
    Phone(클라이언트) "AdditionalRatePolicy가 계약위반, 0이상 보장못함? 서브타입 아님"
- 슈퍼타입의 불변식, 서브타입에서도 반드시 유지 
    AdditionalRatePolicy의 next는 null이면 안된다. 객체생성~소멸까지 
    하지만 protected라서 자식이 임의로 변경가능함 (make private 또는 부모가 불변식 체크)
    
    
2. 교체 가능한 타입과 관련된 '가변성 규칙(variance rules)'
- 서브타입의 메서드 파라미터는 반공변성 필요
- 서브타입의 리턴 타입은 공변성(covariance) 필요 
- 서브타입은 슈퍼타입이 발생시키는 예외와 다른 타입의 예외 발생 불가능 

Phone에 요금청구서 발생시키는 publishBill 메서드 추가 
가입자에게 청구할 요금 담은 Bill 인스턴스 생성 후 반환 
publishBill 메서드에서 calculateFe 반환값 Bill 생성자에 전달 
청구서 요금 최소한 0보다 크거나 같아야 함 :
```
assert result.isGreaterThanOrEqual(Money.ZERO);
assert calls != null; //caclulateFee(calls목록) 총 합 
```
기본정책 구현 BasicRatePolicy 부가정책 구현 AdditionalRatePolicy 둘다 (추상 클래스)에
사전조건 사후조건 추가

