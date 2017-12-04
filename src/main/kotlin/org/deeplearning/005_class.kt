package org.deeplearning

class Empty

class Invoice {}

/*
Functions, properties and classes, objects and interfaces can be declared on the "top-level", i.e. directly inside a package:
    If you do not specify any visibility modifier, public is used by default, which means that your declarations will be visible everywhere;
    If you mark a declaration private, it will only be visible inside the file containing the declaration;
    If you mark it internal, it is visible everywhere in the same module;
    protected is not available for top-level declarations.
 */
fun baz() {}

private fun pbaz() {}
class Bara {}

public var bar: Int = 4
internal val baz = 5

/*
For members declared inside a class:

private means visible inside this class only (including all its members);
protected — same as private + visible in subclasses too;
internal — any client inside this module who sees the declaring class sees its internal members;
public — any client who sees the declaring class sees its public members.
NOTE for Java users: outer class does not see private members of its inner classes in Kotlin.

If you override a protected member and do not specify the visibility explicitly, the overriding member will also have protected visibility.


Modules:
The internal visibility modifier means that the member is visible within the same module. More specifically, a module is a set of Kotlin files compiled together:
    an IntelliJ IDEA module;
    a Maven project;
    a Gradle source set;
    a set of files compiled with one invocation of the Ant task.
 */
open class Outer {
  private val a = 1
  protected open val b = 2
  internal val c = 3
  val d = 4  // public by default

  protected class Nested {
    public val e: Int = 5
  }
}

class Subclass : Outer() {
  // a is not visible
  // b, c and d are visible
  // Nested and e are visible

  override val b = 5   // 'b' is protected
}

/*
Here the constructor is private. By default, all constructors are public, which effectively amounts to them being visible everywhere where the class is visible (i.e. a constructor of an internal class is only visible within the same module).
 */
class Unrelated private constructor(o: Outer) {
  // o.a, o.b are not visible
  // o.c and o.d are visible (same module)
  // Outer.Nested is not visible, and Nested::e is not visible either
}

/*
Interfaces in Kotlin are very similar to Java 8. They can contain declarations of abstract methods, as well as method implementations. What makes them different from abstract classes is that interfaces cannot store state. They can have properties but these need to be abstract or to provide accessor implementations.

 A property declared in an interface can either be abstract, or it can provide implementations for accessors. Properties declared in interfaces can't have backing fields, and therefore accessors declared in interfaces can't reference them

Classes, objects, interfaces, constructors, functions, properties and their setters can have visibility modifiers. (Getters always have the same visibility as the property.) There are four visibility modifiers in Kotlin: private, protected, internal and public. The default visibility, used if there is no explicit modifier, is public
 */
interface MyInterface {
  fun bar()

  fun foo() {} // optional body

  abstract var prop: Int // abstract

  val propertyWithImpl: String
    get() = "foo"
}

class MyChild(override var prop: Int) : MyInterface {
  override fun bar() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

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

interface Clickable {
  fun click()
  fun show(): Unit {
    println("clickable")
  }
}

interface Focusable {
  fun show(): Unit {
    println("focusable")
  }
}

class Button : Clickable, Focusable {
  override fun click() {
    println("click")
  }

  override fun show() {
    super<Focusable>.show()
    super<Clickable>.show()
  }
}

class User1(val nickName: String, isSubscribed: Boolean = true)
class User2(_nickName: String) {
  val nickname = _nickName
}

class Secret private constructor()


interface PersonInfo {
  val name: String
  val id: Long
}

class User3 constructor(_nickname: String) {
  val nickname: String

  init {
    nickname = _nickname
  }
}


fun main(args: Array<String>) {
  println("aa = ${AA().name}")
  println("show = ${Button().show()}")
}