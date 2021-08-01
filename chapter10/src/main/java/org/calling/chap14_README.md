## INIT
#14 일관성 있는 협력 
## p471 기본 정책 확장
  고정요금방식 (#11의 일반 요금제), 시간대별 방식(#11의 심야할인 요금제), 요일별 방식, 구간별 방식(누진)

## p473 고정요금방식 구현하기     
  RegularPolicy -> FixedFeePolicy 클래스명 바꾸기

## p474 시간대별 방식 구현하기
  시간대별 : 일자/시간 분할 필요
  TimeOfDayDiscountPolicy -- splitByDay() --> Call -- splitByDay() -> DateTimeInterval
  => TimeOfDayDiscountPolicy는 통화기간 아는 Call에게 일자별 통화기간 분리 요청 
  => Call은 이 요청, 기간을 저장하고 있는 DateTimeInterval (split(days))에게 위임
  TimeOfDayDiscountPolicy from(interval), to(interval) 
  => DateTimeInterval(곧 Call)이 반환한 '날짜별 분할 기간 <List>' for문 돌리며 시간대별 기준 from, to 얻음
 
 ## p483 요일별 방식 구현하기
  7개 요일 list보다는 DayOfWeekDiscountRule 클래스 선언
  DiscountPolicy는 DayOfWeekDiscountRule 차용, 시간대별 방식
  
## p484 구간별 방식 구현하기 
  고정요금 FixedFee , 시간대별 TimeOfDayDiscount, 요일별 DayOfWeekDiscount 개념 연관되나 설계 재각각 
  함부로 코드 재사용을 위한 상속은 절대 NO
 
 ### Capsulation 변하는 개념만 캡슐화 
 ### 서브타입 캡슐화 by 인터페이스 상속 - 변하는 부분은 변하지 않는 부분을 상속, 서브타입
 ### 객체 캡슐화 by 합성 - Policy 등 
  기본정책은 1개 + 규칙으로 구성되고, 그 규칙 1개는 '적용조건'(변하는 것) 과 '단위요금(변치 않는 것)' 조합이다.
  
## p499 변경 캡슐화하기
 BasicRatePolicy 1=>n FeeRule => FeeCondition 
 (FeeCondition 의 subclass : TimeOfDay... , DayOfWeek... , Duration...)
 FeeRule 의 인스턴스변수 feePerDuration (단위요금, 변치않는 것) 
 FeeRule -> FeeCondition 합성, 상속x 구체 condition 서브타입 알지 못하고 의존.
 
 ## p500 협력 패턴 설계하기 
 fee = calculateFee(phone) -> BasicRatePolicy : 
 fee = calculateFee(call) 1 -> n FeeRule          : List<Call> 전체 타입 * 각 1개 당 복수 메시지
 FeeRule은 1개의 Call 요금 계산 책임 수행 
 통화시간  각'규칙'의 '적용조건' 만족 구간으로 "분리"  : FeeCondition 에 위임 
 + 각 분리구간에 '단위요금 적용'                  : FeeRule에 위임 (그 feePerDuration)
 intervals = findTimeIntervals(call) 1 v 1 FeeCondition (INTERFACE 즉 추상화)
   
 ## p502 추상화 수준에서 협력 패턴 구현하기 
 FeeCondition // call 인자 통화기간 중 '적용 조건' 만족 기간 구한 후 List에 담아 반환
 FeeRule // calculateFee(call), FeeCondition에게 findTimeIntervals(call)로
           조건 만족 시간 목록 반환 받은 것에 + feePerDuration 단위요금 값 이용 요금 계산
  
  ** reduce map stream api  
 ## p505 구체적인 협력 구현하기(서브클래싱)
 DurationFeeCondition, FixedFeeCondition, TimeOfDayFeeCondition