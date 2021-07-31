package basic

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import java.lang.Exception
import java.lang.IllegalArgumentException

/**
 * https://medium.com/canopas/rx-java-basic-with-simplest-rx-examples-for-beginners-e55d5e54462d
 * RxJava basic example
 */

class SimpleRx {}

private val compositeDisposable = CompositeDisposable()

fun main() {
    rxExample6(3)
}

fun rxExample1() {
    val list: List<String> = listOf("1", "2", "3", "4", "5", "6")

    list.toObservable() // list를 Observable로 변환한다.
        .subscribeBy(       // subscribeBy()와 subscribeBy{ }는 무슨차이가 있을까
            onNext = { println(it) },
            onError = { it.printStackTrace() },
            onComplete = { println("onComplete") }
        )
}

// just, fromArray : 해당 아이템들을 Observable로 만들어 준다 (아이템을 발행한다)

/**
 * http://reactivex.io/documentation/ko/operators/just.html
 * just operator에 대한 설명.
 */

fun rxExample2() {
    // just는 최대 10개의 아이템만 넣을 수 있다.
    // 아이템의 타입은 제네릭인 T 타입니다. (하나로 고정)
    Observable.just("Hello", "world", "How", "are", "you", "item6", "item7", "item8", "item9", "item10")
        .subscribe(
            { value -> println("Received : $value") },  // 여기는 왜 onNext와 같이 이름을 작성해서 전달할 수 없는가
            { error -> println("Error : $error") },
            { println("onComplete") }
        ).addTo(compositeDisposable)
}

fun rxExample3() {
    Observable.fromArray("Apple", "Orange", "Banana")
        .subscribe { println(it) }
        .addTo(compositeDisposable)
}

fun rxExample4() {
    Observable.fromIterable(listOf("Titan", "Fastrack", "Sonata"))
        .subscribe(
            { value -> println("Received : $value") },  // 여기는 왜 onNext와 같이 이름을 작성해서 전달할 수 없는가
            { error -> println("Error : $error") },
            { println("onComplete") }
        )
}

fun rxExample5() {
    getObservableFromList(listOf("Summer", "Winter", "Monsoon"))
        .subscribe(
            { value -> println("Received : ${value}") },
            { error -> println("Error : $error") },
            { println("Done") }
        ).addTo(compositeDisposable)
}

private fun getObservableFromList(myList: List<String>) =
    Observable.create<String> { emitter ->
        myList.forEach { kind ->
            if (kind == "") {
                emitter.onError(Exception("Nothing to show"))
            }
            emitter.onNext(kind)
        }
        emitter.onComplete()
    }

/**
 * buffer operator ->
 */

fun rxExample6(bufferCount: Int) {
    Observable.just("A", "B", "C", "D", "E")
        .buffer(bufferCount)
        .subscribeBy(
            onNext = { println("Received : $it") },
            onError = { println(it.printStackTrace()) },
            onComplete = { println("Done") }
        )
}