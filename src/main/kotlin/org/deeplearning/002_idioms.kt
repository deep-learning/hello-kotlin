package org.deeplearning

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.coroutines.experimental.buildIterator

// https://kotlinlang.org/docs/reference/idioms.html
// https://kotlinlang.org/docs/reference/coding-conventions.html


/*
In some cases functions with no arguments might be interchangeable with read-only properties. Although the semantics are similar, there are some stylistic conventions on when to prefer one to another.

Prefer a property over a function when the underlying algorithm:
    does not throw any Exceptions
    has a O(1) complexity
    is cheap to calculate (or caсhed on the first run)
    returns the same result over invocations
 */
object a001_idioms {
    interface Bar
    interface Foo<out T : Any> : Bar {
        fun foo(a: Int): T
    }

    open class Human(id: Int, name: String)

    interface WorkNotes

    class Person(
        id: Int,
        name: String,
        surname: String
    ) : Human(id, name),
        WorkNotes {
        // ...
    }

    // Creating DTOs (POJOs/POCOs)
    data class Customer(val name: String, val email: String)

    /*
    getters (and setters in case of vars) for all properties
    equals()
    hashCode()
    toString()
    copy()
    component1(), component2(), …, for all properties
     */
    val c1 = Customer("n", "e")

    val list = listOf(1, 2, 3, 4)
    // default value for function parameters
    fun foo(a: Int = 0, b: String = "") {
        list.filter { x -> x > 0 }
                .filter { it < 10 }
                .map { it * 2 }


        // String Interpolation
        val name = "Zhenglai"
        println("Name $name")

        // instance check
        when (name) {
            is String       -> "is String"
            is CharSequence -> "is char seq"
            else            -> "not a String"
        }

        // traversing a map/list pairs
        // readonly map
        val map = mapOf(1 to 2, 2 to 3)
        for ((k, v) in map) {
            println("$k -> $v")
        }
        println(map[1])

        // ranges
        for (i in 1..100) {
        } // closed
        for (i in 1 until 100) {
        } // half-open end
        for (x in 2..10 step 2) {
        }
        for (x in 10 downTo 1 step 2) {
        }
        for (x in 1..10) {
        }

        // readonly list
        val list = listOf("a", "b", "c")

        // lazy property
        val p: String by lazy { "lazy str" }

        // extension functions
        fun String.spaceToCamelCase() {}
        "Convert this to camelcase".spaceToCamelCase()

        // if not null shorthand
        val files = File("test").listFiles()
        println(files?.size)

        // if not null & else shorthand
        println(files?.size ?: "empty")

        // executing a statement if null
        val values = mapOf("a" to 1)
        val email = values["email"] ?: throw IllegalStateException("Email is missing")


        // execute if not null
        email?.let {
            // execute this block if not null
        }

        // map nullable value if not null
        fun transformVal(x: Int) = x

        val defaultValueIfValueIsNull = 12
        val mapped = email?.let { transformVal(it) } ?: defaultValueIfValueIsNull

        // return on when statement
        fun transform(color: String): Int {
            return when (color) {
                "Red"   -> 0
                "Green" -> 1
                "Blue"  -> 2
                else    -> throw IllegalAccessException("Invalid color param value")
            }
        }

        // try/catch expression
        fun test() {
            val result = try {
                12
            } catch (e: ArithmeticException) {
                throw IllegalStateException(e)
            }

            val used = 12 + result
        }

        // if expression
        fun foo(param: Int) {
            val result = if (param == 1) {
                "one"
            } else if (param == 2) {
                "two"
            } else {
                "three"
            }
        }

        // builder style usage of methods that return Unit
        // todo revisit
        fun arrayOfMinusOnes(size: Int): IntArray {
            return kotlin.IntArray(size).apply {
                fill(-1)
                filter { it > 12 }
                forEach { println(it) }
            }
        }

        // single expression function
        fun theAnswer() = 12

        // equal to
        fun theAnswer2(): Int {
            return 12;
        }

        fun transform2(color: String): Int = when (color) {
            "red" -> 0
            else  -> throw IllegalAccessException("invalid color param value")
        }

        // call multiple methods on an object instance (with)
        class Turtle {
            fun penDown() {}
            fun penUp() {}
            fun turn(degrees: Double) {}
            fun forward(pixels: Double) {}
        }

        val myTurtle = Turtle()
        with(myTurtle) {
            penDown()
            for (i in 1..5) {
                forward(100.0)
                turn(99.0)
            }
            penUp()
        }

        // Java7's try with resources
        val stream = Files.newInputStream(Paths.get("/tmp/somefile"))
        stream.buffered().reader().use { reader ->
            println(reader.readText())
        }

        // consuming a nullable Boolean
        val b: Boolean? = null
        if (b == true) {

        } else {
            // b is false or null
        }
    }

    // singleton
    object Resouce {
        val name = "Name"
    }

    // todo revisit
    // Convenient form for a generic function that requires the generic type informationin
    //    inline fun <reified T: Any> Gson.fromJson(json: JsonElement): T = this.fromJson(json, T::class.java)


}