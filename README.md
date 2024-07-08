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