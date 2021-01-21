package be.ioosterl.explore.java9.optional;

import org.apache.commons.io.output.TeeOutputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalTest {
  private ByteArrayOutputStream standardOutCaptured;
  private PrintStream standardOut;

  @BeforeEach
  void forkStandardOut() {
    standardOut = System.out;
    standardOutCaptured = new ByteArrayOutputStream();
    System.setOut(new PrintStream(new TeeOutputStream(standardOut, standardOutCaptured)));
  }

  @AfterEach
  void restoreStandardOut() {
    System.setOut(standardOut);
  }

  @Test
  void ifPresentOrElseWithPresent() {
    Optional<String> nonEmptyOptional = Optional.of("Java 9");
    nonEmptyOptional.ifPresentOrElse(this::sayHiTo, () -> sayHiTo("World"));
    assertThat(getCaptupredOutputTrimmed()).isEqualTo("Hello Java 9");
  }

  @Test
  void ifPresentOrElseWithEmpty() {
    Optional<String> emptyOptional = Optional.empty();
    emptyOptional.ifPresentOrElse(this::sayHiTo, () -> sayHiTo("World"));
    assertThat(getCaptupredOutputTrimmed()).isEqualTo("Hello World");
  }

  @Test
  void orWithNonEmptyOptional() {
    Optional.of("Java 9")
        .or(() -> Optional.of("World"))
        .ifPresent(this::sayHiTo);
    assertThat(getCaptupredOutputTrimmed()).isEqualTo("Hello Java 9");
  }

  @Test
  void orWithEmptyOptionalPreJava9() {
    sayHiTo(Optional.<String>empty()
        .orElse("World"));
    assertThat(getCaptupredOutputTrimmed()).isEqualTo("Hello World");
  }

  @Test
  void orWithEmptyOptional() {
    Optional.empty()
        .or(() -> Optional.of("World"))
        .ifPresent(s -> System.out.println("Hello " + s));
    assertThat(getCaptupredOutputTrimmed()).isEqualTo("Hello World");
  }

  @Test
  void orWithMultipleEmptyOptionalsPreJava9() {
    Optional<String> first = Optional.empty();
    Optional<String> second = Optional.empty();
    Optional<String> third = Optional.empty();
    Optional<String> fourth = Optional.of("World");
    Optional<String> chosen;
    if (first.isPresent()) {
      chosen = first;
    } else if (second.isPresent()) {
      chosen = second;
    } else if (third.isPresent()) {
      chosen = third;
    }  else {
      chosen = fourth;
    }
    chosen.ifPresent(this::sayHiTo);
    assertThat(getCaptupredOutputTrimmed()).isEqualTo("Hello World");
  }

  @Test
  void orWithMultipleEmptyOptionals() {
    Optional.empty()
        .or(Optional::empty)
        .or(Optional::empty)
        .or(() -> Optional.of("World"))
        .ifPresent(System.out::println);
    assertThat(getCaptupredOutputTrimmed()).isEqualTo("Hello World");
  }


  private void sayHiTo(String name) {
    System.out.println("Hello " + name);
  }

  private String getCaptupredOutputTrimmed() {
    return standardOutCaptured.toString().trim();
  }

}
