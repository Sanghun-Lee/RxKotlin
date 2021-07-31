package basic

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import io.reactivex.functions.BiFunction

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
        .subscribeBy(       // TODO subscribeBy()와 subscribeBy{ }는 무슨차이가 있을까
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

// TODO subscribeBy와 subscribe함수는 동작에서 어떤 차이가 있을까

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
 * buffer operator -> Observable이 발행한 아이템을 버퍼에 모아서 전달해준다.
 * // TODO 한 번에 buffer count만큼 발행이 되는지, 발행된 아이템을 buffer가 모아서 소비하는지는 좀 더 살펴봐야 한다.
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

/**
 * map operator : stream의 map과 동일한 역할을 수행한다. -> 아이템을 하나 받아서 해당 아이템을 연산을 수행한 아이템으로 변경한다.
 * filter operator : stream의 filter와 동일한 역할을 수행한다. -> filter에 있는 식에 true를 만족하는 아이템만 따로 추려낸다.
 */
fun rxExample7() {
    val observable = Observable.fromArray(1, 2, 3, 4)
    val transformation = observable.map { e -> e * 2 }
    transformation.filter { e -> e > 2 }
        .subscribe(
            { value -> println("Received : $value") },
            { error -> println("Error : $error") },
            { println("Done") }

        ).addTo(compositeDisposable)
}

/**
 * subscribeOn(Schedulers) : Observable의 아이템을 얻기 위한 작업을 해당 Schedulers에서 처리한다 (네트워크 호출이나, DB 접근과 같은 행동에 사용)
 * observeOn(Schedulers) : Observable의 동작이 끝나고 나면, 결과를 observeOn의 Schedulers로 가져와서 사용한다.
 */

fun rxExample8() {
    Observable.range(1, 20)     // 1
        .subscribeOn(Schedulers.io())   // 3
//        .observeOn(AndroidSchedulers.mainThread())        // 4
        .filter { e ->      // 2
            return@filter e % 2 == 0
        }
        .subscribe(
            { value -> println("Received : $value") },
            { error -> println("Error : $error") },
            { println("Done") }
        ).addTo(compositeDisposable)
}

// zip operator
fun rxExample9() {
    val list: List<String> = listOf("1", "2", "3", "4", "5", "6", "7")
    val numObservable = list.toObservable()
    val charObservable = Observable.just("A", "B", "C", "D", "E", "F", "G", "H")
    val zipper = BiFunction<String, String, String> { t1, t2 ->
        "$t1-$t2"
    }

    Observable.zip(numObservable, charObservable, zipper)
        .subscribeOn(Schedulers.io())
        .subscribe(
            { value -> println("Received : $value") },
            { error -> println("Error : $error") },
            { println("Done") }
        ).addTo(compositeDisposable)
}