import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class LambdaList<T> {
  List<T> list;

  public static <T> LambdaList<T> of(T... varargs) {
    List<T> list = new ArrayList<>();
    for (T e : varargs) {
      list.add(e);
    }
    return new LambdaList<T>(list);
  }  

  private LambdaList(List<T> list) {
    this.list = list;
  }

  public static <T> LambdaList<T> generate(int count, Supplier<T> s) {
    // TODO
    return null;
  }

  public <V> LambdaList<V> map(Function<? super T, ? extends V> f) {
    List<V> newList = new ArrayList<V>();
    for (T i: list) {
      newList.add(f.apply(i));
    }
    return new LambdaList<V>(newList);
  }

  public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator) {
    // TODO
    return null;
  }

  public LambdaList<T> filter(Predicate<? super T> predicate) {
    // TODO
    return null;
  }

  public void forEach(Consumer<? super T> action) {
    // TODO
  }

  public String toString() {
    return list.toString();
  }
}
