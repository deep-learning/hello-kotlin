package org.deeplearning.java;

@FunctionalInterface
public interface MyInterface {

  int myMethod();

  default String sayHello() {
    return "hello";
  }

  static void myStaticMethod() {
    System.out.println("MyInterface.myStaticMethod()");
  }

}
