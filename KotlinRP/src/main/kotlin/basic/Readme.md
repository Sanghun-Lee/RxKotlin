# RxJava for beginner

### 1. RxJava를 사용하는 동기

- Rx는 주로 백그라운드에서 작업을 처리할 때 사용된다.
- 네트워크처럼 시간이 걸리는 작업이 Main Thread에 상주하고 있으면 화면이 응답하지 않아서 사용자 반응에 문제가 생긴다.
- 그래서 네트워크 작업은 백그라운드 스레드에서 돌려야 하는데, RxJava가 이러한 백그라운드 스레드 관리를 도와준다.

### 2. RxJava

#### RxJava는 3개의 O로 구성된다.

- Observable
- Observer
- Operator

RxJava는 stream처럼, `Observable`이 리스트에서 아이템을 발행한다.

발행된 아이템들은 `Observers`에 의해 소비된다.

> Observers는 Subscribers라고도 언급되어진다.
>
> Observable이 발행한 아이템을 Observers(Subscribers)들이 subscribe(구독)하여 소비된다.

Observable이 발행한 아이템들은 `Operators`를 사용하여 좀 더 수정된다.

> Observable은 Subscribers가 없다면 아이템이 발행되지 않는다.

### 3. onNext, onError, onComplete

- Subscribers.onNext() : Observable이 아이템 하나 발행할 때 마다 실행되는 함수
- Subscribers.onError() : 아이템을 처리할 때(onNext) 에러가 발생할 시 호출되는 함수
- Subscribers.onComplete() : 발행되 아이템을 모두 처리하였을 때, 실행되는 함수