package be.ioosterl.explore.java9;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ListFactoryMethods {

  @Test
  void listOf() {
    List<Integer> ints = List.of(1, 2, 3, 4, 5);
    assertThat(ints).hasSize(5);
  }

  @Test
  void listOfMutability() {
    List<Wrapper> wrapperList = List.of(new Wrapper(1), new Wrapper(2), new Wrapper(3));
    assertThat(wrapperList).hasSize(3);
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .describedAs("Lists created with java.util.List.of(E...) are structurally immutable.")
        .isThrownBy(() -> wrapperList.add(new Wrapper(4)));
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .describedAs("Elements can't be replaced due to structural immutability")
        .isThrownBy(() -> wrapperList.set(0, new Wrapper(5)));
    assertThatNoException()
        .describedAs("The elements of the list can be modified as long as they are not immutable.")
        .isThrownBy(() -> wrapperList.get(0).setValue(9));
  }

  @Test
  void listOfEmpty() {
    List<Integer> myList = List.of();
    assertThat(myList).isEmpty();
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .describedAs("List.of() produces immutable lists.")
        .isThrownBy(() -> myList.add(1));
  }

  @Test
  void listOfArray() {
    Integer[] ints = {1, 2, 3};
    List<Integer> intList = List.of(ints);

    assertThat(intList).hasSize(3);

    assertThat(intList.get(1)).isEqualTo(ints[1]);
    ints[1] = 10;
    assertThat(intList.get(1))
        .describedAs("Change the array should not change the list")
        .isNotEqualTo(ints[1]);
  }

  private static class Wrapper {
    private Integer value;

    public Wrapper(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    public void setValue(Integer value) {
      this.value = value;
    }
  }
}
