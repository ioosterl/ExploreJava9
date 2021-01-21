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
    assertThat(standardOutCaptured.toString()).isEqualTo("Hello Java 9");
  }

  @Test
  void ifPresentOrElseWithEmpty() {
    Optional<String> emptyOptional = Optional.empty();
    emptyOptional.ifPresentOrElse(this::sayHiTo, () -> sayHiTo("World"));
    assertThat(standardOutCaptured.toString()).isEqualTo("Hello World");
  }

  private void sayHiTo(String name) {
    System.out.print("Hello " + name);
  }
}
