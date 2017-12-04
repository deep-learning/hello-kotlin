package org.deeplearning.java;

import java.util.function.Predicate;

public class ImplementPredicates {

  public static final Predicate<String> LENGTH_FIVE = s -> s.length() == 5;
  public static final Predicate<String> STARTS_WITH_S = s -> s.startsWith("S");;
}
