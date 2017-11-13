package org.deeplearning

class Empty

class Invoice {}

/*
A class in Kotlin can have a primary constructor and one or more secondary constructors. The primary constructor is part of the class header: it goes after the class name (and optional type parameters)
If the primary constructor does not have any annotations or visibility modifiers, the constructor keyword can be omitted
 */

class Person constructor(firstName: String) {
    /*
    The primary constructor cannot contain any code. Initialization code can be placed in initializer blocks, which are prefixed with the init keyword
     */
    init {
        println("person init with value $firstName")
    }

    /*
    arameters of the primary constructor can be used in the initializer blocks. They can also be used in property initializers declared in the class body
     */
    val personKey = firstName.toUpperCase()

    // Classes in Kotlin can have properties. These can be declared as mutable, using the var keyword or read-only using the val keyword
    var city: String = ""
    var state: String? = null


    /*
    property syntax:
    var <propertyName>[: <PropertyType>] [= <property_initializer>]
        [<getter>]
        [<setter>]
     */
    var allByDefault: Int? = 1
    val initialized = 1 // type Int, default getter & setter

    val inferredTyep = 1 // type Int, default getter only

    private val size = 12

    // custom getter
    val isEmpty: Boolean
        get() = this.size == 0

    fun setDataFromString(value: String) {}

    var stringRepresentation: String
        get() = this.toString()
        set(value) {
            // By convention, the name of the setter parameter is value, but you can choose a different name if you prefer
            // parse str & assigns values to other properties
            setDataFromString(value)
        }

    // Since Kotlin 1.1, you can omit the property type if it can be inferred from the getter:
    val isEmpty2 get() = this.size == 0

    var setterVisibility: String = "abc"
        private set // the setter is private and has the default implementation

    //    var setterWithAnnotation: Any? = null
    //        @Inject set // annotate the setter with Inject


    /*
    Classes in Kotlin cannot have fields. However, sometimes it is necessary to have a backing field when using custom accessors. For these purposes, Kotlin provides an automatic backing field which can be accessed using the field identifier
    A backing field will be generated for a property if it uses the default implementation of at least one of the accessors, or if a custom accessor references it through the field identifier
     */
    var counter = 0 // the initializer value is written directly to the backing field
        set(value) {
            if (value >= 0) field = value
        }

    /*
    If you want to do something that does not fit into this "implicit backing field" scheme, you can always fall back to having a backing property:

    In all respects, this is just the same as in Java since access to private properties with default getters and setters is optimized so that no function call overhead is introduced.
     */
    private var _table: Map<String, Int>? = null
    public val table: Map<String, Int>
        get() {
            if (_table == null) {
                _table = HashMap()
            }
            return _table ?: throw AssertionError("Set to null by another thread")
        }

    /*
    Compile-Time Constants
    Properties the value of which is known at compile time can be marked as compile time constants using the const modifier. Such properties need to fulfil the following requirements:
        1. Top-level or member of an object
        2. Initialized with a value of type String or a primitive type
        3. No custom getter
     */

    @Deprecated(Constants.SUBSYSTEM_DEPRECATED)
    fun foo() {
    }

    fun bar() {
        foo()
    }

    // Late-Initialized Properties
    /*
    Normally, properties declared as having a non-null type must be initialized in the constructor. However, fairly often this is not convenient. For example, properties can be initialized through dependency injection, or in the setup method of a unit test. In this case, you cannot supply a non-null initializer in the constructor, but you still want to avoid null checks when referencing the property inside the body of a class.
    The modifier can only be used on var properties declared inside the body of a class (not in the primary constructor), and only when the property does not have a custom getter or setter. The type of the property must be non-null, and it must not be a primitive type.

Accessing a lateinit property before it has been initialized throws a special exception that clearly identifies the property being accessed and the fact that it hasn't been initialized.
     */

    lateinit var subject: String
    fun setup() {
        subject = "xx"
    }

    fun test() {
        println(subject.length)
    }
}

object Constants {
    const val SUBSYSTEM_DEPRECATED: String = "The subsystem is deprecated"
}

/*
In fact, for declaring properties and initializing them from the primary constructor, Kotlin has a concise syntax:
Much the same way as regular properties, the properties declared in the primary constructor can be mutable (var) or read-only (val).
If the constructor has annotations or visibility modifiers, the constructor keyword is required, and the modifiers go before it
 */

class Customer public constructor(val firstName: String, val lastNamE: String, var age: Int) {
    /*
    If the class has a primary constructor, each secondary constructor needs to delegate to the primary constructor, either directly or indirectly
     */
    constructor(parent: Person) : this("fn", "ln", 12) {

    }
}

/*
If a non-abstract class does not declare any constructors (primary or secondary), it will have a generated primary constructor with no arguments. The visibility of the constructor will be public.
 */
class DontCreateMe private constructor() {

}

/*
On the JVM, if all of the parameters of the primary constructor have default values, the compiler will generate an additional parameterless constructor which will use the default values. This makes it easier to use Kotlin with libraries such as Jackson or JPA that create class instances through parameterless constructors.
 */

class AA(val name: String = "name") {

}

class Example // Implicitly inherits from Any
/*
Any is not java.lang.Object; in particular, it does not have any members other than equals(), hashCode() and toString()
 */

open class Base(p: Int) {
    open fun v() {}
    open fun g() {}
    fun u() {}

    open val x: Int
        get() {
            return 12
        }
}

open class Derived(p: Int) : Base(p) { // class header
    override fun v() {
        super.v()
    }

    /*
    A member marked override is itself open, i.e. it may be overridden in subclasses. If you want to prohibit re-overriding, use final
     */
    final override fun g() {
        super.g()
    }

    /*
    operties declared on a superclass that are then redeclared on a derived class must be prefaced with override,
    and they must have a compatible type. Each declared property can be overridden by a property with an initializer or by a property with a getter method.

    You can also override a val property with a var property, but not vice versa
    Code in a derived class can call its superclass functions and property accessors implementations using the super keyword
     */
    override val x: Int get() = super.x
}

interface Foo {
    val count: Int
}

// Note that you can use the override keyword as part of the property declaration in a primary constructor.
class Bar1(override val count: Int) : Foo

class Bar2 : Foo {
    override var count: Int = 0
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    inner class Baz {
        fun g() {
            // Calls Foo's implementation of f()
            super@Bar2.hashCode()
        }
    }
}


/*
In Kotlin, implementation inheritance is regulated by the following rule: if a class inherits many implementations of the same member from its immediate superclasses, it must override this member and provide its own implementation (perhaps, using one of the inherited ones)
 */
open class A {
    open fun f() {
        println("A")
    }

    fun a() {
        println("a")
    }
}

interface B {
    // interface members are 'open' by default
    fun f() {
        println("B")
    }

    fun b() {
        println("b")
    }
}

class C() : A(), B {
    // The compiler requires f() to be overridden:
    override fun f() {
        super<A>.f() // call to A.f()
        super<B>.f() // call to B.f()
    }
}

/*
A class and some of its members may be declared abstract. An abstract member does not have an implementation in its class. Note that we do not need to annotate an abstract class or function with open – it goes without saying
We can override a non-abstract open member with an abstract one
 */
open class BaseA {
    open fun f() {}
}

abstract class DerivedA : BaseA() {
    override abstract fun f()
}

/*
In Kotlin, unlike Java or C#, classes do not have static methods. In most cases, it's recommended to simply use package-level functions instead.
 */

/*
If the class has no primary constructor, then each secondary constructor has to initialize the base type using the super keyword
 In a final class (e.g. a class with no open annotation), open members are prohibited.
 */

fun main(args: Array<String>) {
    println("aa = ${AA().name}");
}