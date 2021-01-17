package be.ioosterl.explore.java9;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class SetFactoryMethods {

  @Test
  void setOf() {
    Set<Integer> set = Set.of(1, 2, 3);
    assertThat(set).hasSize(3);
  }

  @Test
  void setOfWithDuplicates() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> Set.of(1, 2, 2));
  }

  @Test
  void setOfThenAdd() {
    Set<Integer> mySet = Set.of(1,2);
    assertThatExceptionOfType(UnsupportedOperationException.class)
        .isThrownBy(() -> mySet.add(3));
  }

  @Test
  void setOfVarArgsOrArray() {
    Set<Integer> fromArray = Set.of(new Integer[] {1, 2, 3});
    Set<Integer> directly = Set.of(1,2,3);
    assertThat(fromArray).isEqualTo(directly);
    assertThat(fromArray).isNotSameAs(directly);
  }

  @Test
  void setOfWrapperObjects() {
    Set<Wrap<Integer>> set = Set.of(Wrap.around(1), Wrap.around(2));
    assertThat(set).hasSize(2);
    assertThat(set).extracting("value").containsOnly(1,2);

    set.forEach(w -> w.reWrap(w.unWrap()+1));
    assertThat(set).extracting("value").containsOnly(2,3);
  }

  @Test
  void setOfWithNull() {
    assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> Set.of(1,2, null, 3));
  }

}
