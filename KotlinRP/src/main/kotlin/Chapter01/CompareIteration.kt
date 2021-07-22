package Chapter01

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

// 리스트를 다룰 때 iteration을 사용하는 것과
// observerable pattern을 사용하는 것을 비교해 보자.
class CompareIteration {
}

fun iteration(list: List<Any>): Unit {
    val iterator = list.iterator()
    while (iterator.hasNext()) {
        println(iterator.hasNext())
    }
}

fun observerPattern(list: List<Any>): Unit {
    val observable: Observable<Any> = list.toObservable()
    // 데이터가 사용 완료되면 onNext가 계속 호출되어 리스트가 하나씩 출력되고
    // 출력이 끝나면 onComplete가 호출된다.
    observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done") },
    )
}