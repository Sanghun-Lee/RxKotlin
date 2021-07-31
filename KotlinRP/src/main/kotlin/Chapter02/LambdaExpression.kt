package Chapter02

import java.util.*

/**
 * 코틀린 람다표현식
 * 이름없는 익명 함수를 뜻한다.
 * 변수에 이름이 없는 함수를 대입하는거구나.
 */

fun main() {
    val sum = { x: Int, y: Int -> x + y }
    println("Sum ${sum(10, 12)}")
    val anonymusMult = { x: Int -> (Random().nextInt(15) + 1) * x }
    println("random output ${anonymusMult(2)}")
}

/**
 * 순수 함수
 * 함수의 반환값이 인수 / 매개변수에 전적으로 의존하는 함수
 * 같은 input에 같은 output이 나오는 함수
 */

fun pureFunction() {
    println("named pure func square = ${square(3)}")   // 이름이 있는 순수함수
    val qube = { n: Int -> n * n * n }                    // 익명의 람다 순수함수
    println("lambda pure func qube = ${qube(3)}")
}

// 순수함수 square
fun square(n: Int): Int {
    return n * n
}

/** 순수함수는 부작용이 없다. (부작용 : 함수 내 변수 뿐 아니라, 전역변수와 같이 자신의 범위 외부의 상태를 수정하는 것) */

/**
 * 고차 함수
 * 함수를 매개변수의 인자로 받아들이거나, 함수를 반환하는 함수를 고차 함수라고 한다.
 */

fun Int.isEven() = this % 2 == 0

fun highOrderFunc(a: Int, validityCheckFunc: (a: Int) -> Boolean) {
    if (validityCheckFunc(a)) {
        println("a $a is Valid")
    } else {
        println("a $a is Invalid")
    }
}

fun highOrderExample() {
    highOrderFunc(12, { a: Int -> a.isEven() })
    highOrderFunc(19) { a: Int -> a.isEven() }
}

class LambdaExpression {
}