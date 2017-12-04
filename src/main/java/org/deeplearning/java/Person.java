package org.deeplearning.java;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Person {

  private String name;

  public Person() {}

  public Person(final String name) {
    this.name = name;
  }

  public Person(final Person person) {
    this.name = person.name;
  }

  Person(final String... names) {
    this.name = Arrays.stream(names)
        .collect(Collectors.joining(" "));
  }

  public String getName() {
    return name;
  }

  public Person setName(final String name) {
    this.name = name;
    return this;
  }
}
