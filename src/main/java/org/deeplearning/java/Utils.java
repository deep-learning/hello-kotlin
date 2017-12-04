package org.deeplearning.java;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

  public static <T> List<T> filterNull(List<T> list) {
    return list.stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }
}
