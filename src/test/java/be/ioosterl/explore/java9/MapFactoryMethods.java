package be.ioosterl.explore.java9;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class MapFactoryMethods {

  @Test
  void mapOf() {
    //Note, this approach works for up to 10 key-value pairs -> see mapOfArrayOrVargs
    // Map.of("Key 1", "Value 1", "Some Value") won't even compile,
    // so you are forced to specify an even number of arguments
    Map<String, String> map = Map.of("Key 1", "Value 1", "Key 2" ,"Value 2");
    assertThat(map).hasSize(2);
    assertThat(map).containsKeys("Key 1", "Key 2");
    assertThat(map).containsValues("Value 1", "Value 2");
    assertThat(map).containsOnly(Map.entry("Key 1", "Value 1"), Map.entry("Key 2", "Value 2"));
  }

  @Test
  void mapOfNullKeyThrowsException() {
    assertThatExceptionOfType(NullPointerException.class)
        .isThrownBy(() -> Map.of("Key 1", "Value 1", null, "Value 2"));
  }

  @Test
  void mapOfNullValueThrowsException() {
    assertThatExceptionOfType(NullPointerException.class)
        .isThrownBy(() -> Map.of("Key 1", "Value 1", "Key 2", null));
  }

  @Test
  void mapOfArrayOrVarargs() {
    Map.Entry[] entries = IntStream.rangeClosed(1, 15)
        .mapToObj(i -> Map.entry("Key " + i, "Value " + i))
        .toArray(Map.Entry[]::new);
    Map<String, String> map = Map.ofEntries(entries);

    assertThat(map).hasSize(15);
  }

}
