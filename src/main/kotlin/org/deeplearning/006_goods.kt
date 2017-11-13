package org.deeplearning

/*
Kotlin supports extension functions and extension properties.

Extensions are resolved statically

We would like to emphasize that extension functions are dispatched statically, i.e. they are not virtual by receiver type. This means that the extension function being called is determined by the type of the expression on which the function is invoked, not by the type of the result of evaluating that expression at runtime.
 */

// To declare an extension function, we need to prefix its name with a receiver type, i.e. the type being extended
fun <T> MutableList<T>.swap(ix1: Int, ix2: Int) {
    // The this keyword inside an extension function corresponds to the receiver object
    val tmp = this[ix1]
    this[ix2] = this[ix1]
    this[ix1] = tmp
}

fun Any?.toString(): String {
    if (this == null) return "null"
    // after the null check, 'this' is autocast to a non-null type, so the toString() below
    // resolves to the member function of the Any class
    return toString()
}

val l = mutableListOf(1, 2, 3)


open class C1 {
    fun foo(i: Int) {}
}

class D : C1()

fun C1.foo() = "c"

fun D.foo() = "d"

fun printFoo(c: C1) {
    println(c.foo())
    println(c.foo(1))
}

/*
If a class has a member function, and an extension function is defined which has the same receiver type, the same name and is applicable to given arguments, the member always wins.
it's perfectly OK for extension functions to overload member functions which have the same name but a different signature:
 */

fun main(args: Array<String>) {
    l.swap(0, 2)
    println(l)
    println(D())    // "c
}