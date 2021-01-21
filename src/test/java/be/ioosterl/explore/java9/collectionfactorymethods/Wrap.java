package be.ioosterl.explore.java9.collectionfactorymethods;

import java.util.Objects;

class Wrap<T>{
  private T value;

  private Wrap(T value) {
    this.value = value;
  }

  public T unWrap() {
    return value;
  }

  public void reWrap(T value) {
    this.value = value;
  }

  public static <T> Wrap<T> around(T value) {
    return new Wrap<>(Objects.requireNonNull(value));
  }
}
