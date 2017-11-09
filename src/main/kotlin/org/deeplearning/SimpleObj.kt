package org.deeplearning

// https://kotlinlang.org/docs/reference/basic-syntax.html

// top level variabl
val PI = 3.14
var x = 0.0
fun incrementX() {
    x += 1
}

object SimpleObj {
    fun sum(a: Int, b: Int): Int {
        return a + b
    }

    // Function with an expression body and inferred return type:
    fun sum1(a: Int, b: Int) = a + b

    // Unit return type can be omitted:
    fun printSum(a: Int, b: Int): Unit {
        println("$a + $b = ${a + b}")

        // Assign-once (read-only) local variable
        val b = 2   // `Int` type is inferred
        val c: Int  // Type required when no initializer is provided
        c = 3       // deferred assignment

        // mutable variable
        var x = 5 // `Int` type is inferred
        x += 1

        // Unlike Java, block comments in Kotlin can be nested.
        /*/**/*/
    }


    // string templates
    fun str() {
        var a = 1
        val s1 = "a is $a"
        a = 2
        val s2 = "${s1.replace("is", "was")}, but now is $a"
    }

    // conditional expression
    fun maxOf(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    // use if as expression
    fun maxOf2(a: Int, b: Int) = if (a > b) a else b

    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    fun printProduct(s1: String, s2: String) {
        val x = parseInt(s1)
        val y = parseInt(s2)

        // Using `x * y` yields error because they may hold nulls.
        if (x != null && y != null) {
            // x and y are automatically cast to non-nullable after null check
            println(x * y)
        } else {
            println("either '$s1' or '$s2' is not a number")
        }
    }

    fun adfad(s1: String, s2: String) {
        val x = parseInt(s1)
        val y = parseInt(s2)
        if (x == null) {
            println("Wrong number format in arg1: '$s1'")
            return
        }
        if (y == null) {
            println("Wrong number format in arg2: '$s2'")
            return
        }

        // x and y are automatically cast to non-nullable after null check
        println(x * y)
    }

    // The is operator checks if an expression is an instance of a type.
    // If an immutable local variable or property is checked for a specific type, there's no need to cast it explicitly
    fun typeCheckAutoCast(obj: Any): Int? {
        if (obj is String) {
            // auto cast to String in this branch
            return obj.length
        }
        return null
    }

    fun getStringLen(obj: Any): Int? {
        if (obj !is String) return null
        // auto cast to String in this branch
        return obj.length
    }

    fun getStrLenV2(obj: Any): Int? {
        if (obj is String && obj.length > 0) {
            return obj.length
        }
        return null
    }

    val items = listOf("apple", "orange", "kiwi")

    fun forloop() {
        for (item in items) {
            println(item)
        }

        for (ix in items.indices) {
            println("items[$ix] = ${items[ix]}")
        }
    }

    fun whileloop() {
        var ix = 0
        while (ix < items.size) {
            println("items[$ix] = ${items[ix]}")
            ix++
        }
    }


    // when expression
    fun desc(obj: Any): String = when (obj) {
        1          -> "one"
        "hello"    -> "hello"
        is Long    -> "Long"
        !is String -> "Not a String"
        else       -> "Unknown"
    }

    // range
    fun range() {
        if (10 in 1..9 + 1) {
            println("fits in range")
        }

        if (-1 !in 0..items.lastIndex) {
            println("out of range")
        }

        if (items.size !in items.indices) {
            println("out of range")
        }

        for (x in 1..4) {
            println(x)
        }

        // step
        for (x in 1..10 step 2) {
            println(x)
        }

        for (x in 9 downTo 0 step 3) {
            println(x)
        }
    }

    fun collections() {
        for (item in items) {
            println(item)
        }

        // operator in -> collection contains smth
        when {
            "orange" in items -> println("juicy")
            "apple" in items  -> println("fine too")
        }

        // lambda
        items.filter { it.startsWith("a") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEach { println(it) }
    }

    class Rectangle()

    // now `new`
    val rect = Rectangle()
}