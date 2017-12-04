package org.deeplearning.java;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StreamTest {

  public static String getNamesSatisfyingCondition(Predicate<String> condition, String... names) {
    return Stream.of(names)
        .filter(condition)
        .collect(Collectors.joining(", "));
  }


  public static void main(String[] args) {
    System.out.println(Stream.of("aaaa", "bb")
        .sorted(Comparator.comparingInt(String::length).reversed())
        .collect(Collectors.toList())
    );

    System.out.println(Stream.of("aaaa", "bb")
        .sorted(Comparator.comparing(String::length).thenComparing(Comparator.naturalOrder()))
        .collect(Collectors.toList())
    );
  }

  // fork-join hurt performance...
  private static void unordered() {
    System.out.println(Stream.of(1, 2, 3, 4, 4, 5, 6, 8)
        .unordered()
        .parallel()
        .map(StreamTest::delay)
        .findAny());
  }

  public static Integer delay(Integer n) {
    try {
      Thread.sleep((long) (Math.random() * 100));
      System.out.println(Thread.currentThread().getName());
    } catch (InterruptedException ignored) {
    }
    return n;
  }

  private static void streamOps() {
    System.out.println(Stream.of("aa", "bb", "cc")
        .reduce("", String::concat));

    System.out.println(Stream.of("aa", "bb", "cc")
        .collect(StringBuilder::new,
            StringBuilder::append,
            StringBuilder::append
        ).toString());

    System.out.println(Stream.of("aa", "bb", "cc")
        .collect(Collectors.joining()));

    System.out.println(Stream.of("aaa", "bbbbb", "cccccc")
        .collect(Collectors.partitioningBy(
            s -> s.length() % 2 == 0,
            Collectors.counting()
        )));

    System.out.println(DoubleStream.generate(Math::random)
        .limit(1_000_000)
        .summaryStatistics());

    IntStream.range(1, 10)
        .boxed()
        .collect(Collectors.toList());

    IntStream.of(1, 2, 3)
        .sum();

    IntStream.of(1, 2, 3)
        .reduce((x, y) -> x + y)
        .orElse(0);

    IntStream.rangeClosed(1, 10)
        .mapToObj(Integer::valueOf)
        .collect(Collectors.toList());

    IntStream.of(1, 2, 3, 10)
        .collect(ArrayList<Integer>::new, ArrayList::add, ArrayList::addAll);

    List<BigDecimal> collect = Stream.iterate(BigDecimal.ONE, n -> n.add(BigDecimal.ONE))
        .limit(10)
        .collect(Collectors.toList());
    System.out.println("collect = " + collect);

    Stream.iterate(LocalDate.now(), d -> d.plusDays(1L))
        .limit(10)
        .collect(Collectors.toList());
  }

  private static void predicate() {
    System.out.println(getNamesSatisfyingCondition(s -> s.startsWith("a"), "aa", "bb", "ac"));
  }

  private static void optional() {
    Logger.getLogger(StreamTest.class.getName()).info(() -> "wow");
    Optional<String> first = Stream.of("aaa", "aa", "bb")
        .filter(name -> name.startsWith("a"))
        .findFirst();
  }

  private static void doubleSupplier() {
    DoubleSupplier doubleSupplier = new DoubleSupplier() {
      @Override
      public double getAsDouble() {
        return Math.random();
      }
    };
    doubleSupplier = () -> Math.random();
    doubleSupplier = Math::random;
  }

  private static void sorted() {
    Stream.of("aa", "cc", "ddd", "e")
        .sorted(Comparator.naturalOrder())
        .sorted(Comparator.reverseOrder())
        .sorted(Comparator.comparing(String::toLowerCase))
        .sorted(Comparator.comparingInt(String::length))
        .collect(Collectors.toList());
  }

  private static void ctorRef() {
    Stream.of("aaaa bbbb")
        .map(n -> n.split(" "))
        .map(Person::new)
        .forEach(System.out::println);

    Person[] people = Stream.of("aaa", "bbb")
        .map(Person::new)
        .toArray(Person[]::new);
    System.out.println(Arrays.toString(people));
  }

  private static void copyCtor() {
    List<Person> persons = Arrays.asList("aa", "bb")
        .stream()
        .map(Person::new)
        .collect(Collectors.toList());

    List<Person> after = persons.stream()
        .map(Person::new)
        .collect(Collectors.toList());
  }

  private static void methodRef2() {
    StrSplitter strSplitter = new StrSplitter();
    Arrays.asList("a", "b", "c")
        .stream()
        .map(strSplitter::split)
        .forEach(System.out::println);
  }

  private static void methodRef() {
    Consumer<Integer> print = System.out::println;
    Consumer<Integer> integerConsumer = print.andThen(print).andThen(print);
    Stream.of(1, 2, 3)
        .forEach(integerConsumer);
  }

  private static void generate() {
    Stream.generate(Math::random)
        .limit(10)
        .forEach(System.out::println);
  }
}


class StrSplitter {

  char split(String str) {
    return str.charAt(0);
  }
}
