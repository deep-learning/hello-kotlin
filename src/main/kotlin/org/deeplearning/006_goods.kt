package org.deeplearning

/*
Kotlin supports extension functions and extension properties.

Extensions are resolved statically
We would like to emphasize that extension functions are dispatched statically, i.e. they are not virtual by receiver type. This means that the extension function being called is determined by the type of the expression on which the function is invoked, not by the type of the result of evaluating that expression at runtime.
Most of the time we define extensions on the top level, i.e. directly under packages
To use such an extension outside its declaring package, we need to import it at the call site
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


class D1 {
    fun bar() {}

    override fun toString(): String {
        return super.toString()
    }
}

class E1 {
    fun baz() {}

    fun D1.foo() {
        bar() // calls D.bar(), extension receiver
        baz() // calls E1.baz(), dispatch receiver
    }

    fun caller(d: D1) {
        d.foo() // calls the extension function
    }

    // In case of a name conflict between the members of the dispatch receiver and the extension receiver,
    // the extension receiver takes precedence.
    // To refer to the member of the dispatch receiver you can use the qualified this syntax.
    fun D1.bar() {
        toString()              // D.toString()
        this@E1.toString()      // C.toString()
    }
}

// Extensions declared as members can be declared as open and overridden in subclasses. This means that the dispatch of such functions is virtual with regard to the dispatch receiver type, but static with regard to the extension receiver type.
open class DD {}

class D11 : DD() {}

open class CC {
    open fun DD.foo() {
        println("D.foo in C")
    }

    open fun D11.foo() {
        println("D1.foo in C")
    }

    fun caller(d: DD) {
        d.foo() // calls the extension method
    }
}

// todo https://kotlinlang.org/docs/reference/extensions.html

fun printFoo(c: C1) {
    println(c.foo())
    println(c.foo(1))
}

/*
If a class has a member function, and an extension function is defined which has the same receiver type, the same name and is applicable to given arguments, the member always wins.
it's perfectly OK for extension functions to overload member functions which have the same name but a different signature:
 */

// extension properties
// Note that, since extensions do not actually insert members into classes, there's no efficient way for an extension property to have a backing field. This is why initializers are not allowed for extension properties. Their behavior can only be defined by explicitly providing getters/setters.
val <T> List<T>.lastIndex2: Int
    get() = size - 1

class MyClass {
    companion object {}
}

// companion object extension
//define extension functions and properties for the companion object
fun MyClass.Companion.foo() {

}


// On the JVM, if the generated class needs to have a parameterless constructor, default values for all properties have to be specified
data class User(val name: String = "default", val age: Int = -1)
/*
The compiler automatically derives the following members from all properties declared in the primary constructor:
    equals()/hashCode() pair;
    toString() of the form "User(name=John, age=42)";
    componentN() functions corresponding to the properties in their order of declaration;
    copy() function (see below).

data classes have to fulfil the following requirements:
    The primary constructor needs to have at least one parameter;
    All primary constructor parameters need to be marked as val or var;
    Data classes cannot be abstract, open, sealed or inner;
    (before 1.1) Data classes may only implement interfaces.
 */


// Component functions generated for data classes enable their use in destructuring declarations:

fun main(args: Array<String>) {
    l.swap(0, 2)
    println(l)
    println(D())    // "c
    println(null.toString())
    MyClass.foo()
    println(User())
    val jack = User(name = "Jack", age = 1)
    val olderJack = jack.copy(age = 2)
    val (name, age) = olderJack
    println(olderJack)
    println("$name, $age years of age")
}

/*
Standard Data Classes

The standard library provides Pair and Triple. In most cases, though, named data classes are a better design choice, because they make the code more readable by providing meaningful names for properties.
 */