package org.deeplearning


class Box<T>(t: T) {
  var value = t
}

val box: Box<Int> = Box<Int>(1)
// But if the parameters may be inferred, e.g. from the constructor arguments or by some other means, one is allowed to omit the type arguments
val box2 = Box(1)

// Use bounded wildcards to increase API flexibility. First, generic types in Java are invariant, meaning that List<String> is not a subtype of List<Object>
// Java prohibits such things in order to guarantee run-time safety. But this has some implications.
// Java
//interface Collection<E> ... {
//    void addAll(Collection<? extends E> items);
//}
// In "clever words", the wildcard with an extends-bound (upper bound) makes the type covariant.

// In Java we have List<? super String> a supertype of List<Object>.
// The latter is called contravariance, and you can only call methods that take String as an argument on List<? super String>
/*
Joshua Bloch calls those objects you only read from Producers, and those you only write to Consumers. He recommends: "For maximum flexibility, use wildcard types on input parameters that represent producers or consumers", and proposes the following mnemonic:

PECS stands for Producer-Extends, Consumer-Super.
 */

fun <T> minSerializable(first: T, second: T): T
    where T : Comparable<T>, T : java.io.Serializable {
  return if (first <= second) first else second
}

