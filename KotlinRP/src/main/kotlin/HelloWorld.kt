import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

class HelloWorld

fun main() {
    var list: List<Any> = listOf("One", 2, "Three", 4.5, "Five", 6.0f)
    var observable: Observable<Any> = list.toObservable()

    observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("Done!") }
    )
}