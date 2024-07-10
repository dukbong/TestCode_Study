# TestCode_Study

## Test Double 용어 정리

- Dummy : 아무것도 하지 않는 깡통 객체
- Fake : 단순한 형태로 동일한 기능을 수행하지만 프로덕션에서 쓰기에는 부족한 객체
  - ex) FakeRepository
- Stub : 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체이며 그 외에는 응답하지 않는다.
- Spy : Stub이면서 호출된 내용을 기록하여 보여줄 수 있는 객체이며 일부는 실제 객체처럼 동작시키고 일부만 Stubbing할 수 있다.
- Mock : 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체

자주 헷갈리는 것
- Stub은 ***상태 검증 - State Verification***이며 Mock은 ***행위 검증 - Behavior Verification***이다.

테스트 코드는 한 주제씩 작성하기
- if, for등을 이용하면 한 주제가 아닌 여러 주제에 대해 테스트 코드를 짜게 되는데 이는 올바르지 않은 테스트 코드 작성법이다.

제어하지 못하는 것은 최대한 멀리 보내서 테스트 코드를 작성하기 좋게 만들어야 한다.

--- 

deleteAll()과 deleteAllInBatch()

deleteAllInBatch 승
- deleteAllInBatch()는 한번에 지우기 편하다.
- deleteAll()은 Select 후 하나하나 delete한다. (쿼리가 많이 날아간다.)

deleteAll() 승
- deleteAllInBatch() 외래키를 가진 테이블을 생각하고 먼저 지워줘야한다.
- deleteAll() 양방향 관계를 맺고 있다면 아무거나 먼저 지워도 연관 테이블까지 select 후 지우기 때문에 순서가 상관없다.

결론
- deleteAll()은 쿼리가 많이 나가는데 이는 결국 비용이기 때문에 deleteAllInBatch()를 사용하는것이 좋다.

---
private 메소드의 테스트 할 필요 없다!
- 이유는 public 메소드를 테스트할때 테스트가 되기 때문이다.
- 만약 하고 싶다면 객체를 분리할 시점인가를 생각해보면 된다. (ProductNumberFactory.java)
