package org.deeplearning.one

import java.util.*

var counter = 0
val counter1 = 0
const val counter3 = 12

class Rectangle(val width: Int, val height: Int) {
    val isSquare: Boolean
        get() = width == height

    val enum = false
}

enum class Color(val r: Int, g: Int, b: Int) {
    RED(255, 0, 0)
}

val random = Random();
fun createRandomRectangle(): Rectangle {
    return Rectangle(random.nextInt(), random.nextInt())
}

data class Person(val name: String, val age: Int? = null)

class Student(var name: String, var isMarried: Boolean) {
    var isOld: Boolean
        get() {
            return isMarried
        }
        set(value) {
            isMarried = value
        }

    val isOld2: Boolean
        get() = isMarried == true
}

fun max(a: Int, b: Int) = if (a > b) a else b

fun String.lastChar() = this[length - 1]

val String.lastChar: Char
    get() = get(length - 1)

var StringBuilder.lastChar: Char
    get() = get(length - 1)
    set(value: Char) {
        this.setCharAt(length - 1, value)
    }

interface Clickable {
    fun click()
    fun showOff() = println("i am clickable")
}

class Button : Clickable {
    override fun click() {
        println("i am a button")
    }
}

class Person2(val firstName: String, val lastName: String) {
    override fun equals(other: Any?): Boolean {
        val otherPerson = other as? Person2 ?: return false
        return otherPerson.firstName == firstName &&
                otherPerson.lastName == lastName
    }
}

fun strLengthSafe(s: String?) = s?.length ?: 0

fun main(args: Array<String>) {
    val persons = listOf(Person("alice"), Person("zhenglai", age = 12))
    val oldest = persons.maxBy { it.age ?: 0 }
    println("the oldest man is $oldest and ${if ("hello".length > 0) "good" else "bad"}")

    val l = arrayListOf("a")

    println(listOf(12, 13).joinToString("|", prefix = "[", limit = 12))

    val a: Int = 12
    val f = 12.0f
    println("kotlin".lastChar())

    listOf(12, 12)

    for ((k, v) in listOf(1, 2, 3, 4).withIndex()) {
        println("$k -> $v")
    }
}
