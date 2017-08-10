import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * Created by Radu on 8/10/2017.
 */
public class PrimeCollector implements Collector<Integer,
        Map<Boolean, List<Integer>>,
        Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>(){{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> accumulator, Integer element)
                -> {
            accumulator.get(testIfPrimeEfficient(accumulator.get(true), element))
                    .add(element);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (Map<Boolean, List<Integer>> map1,
                Map<Boolean, List<Integer>> map2) -> {
            map1.putAll(map2);
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.CONCURRENT,
                Characteristics.IDENTITY_FINISH));
    }

    /* simple prime testing method
       we test division with all numbers between [2, k]
       where k is the largest number
       that satisfies k^2 < n
     */
    private static boolean testIfPrime(Integer element){
        return IntStream.rangeClosed(2, (int) Math.sqrt(element))
                .boxed()
                .noneMatch(index -> element % index == 0);
    }

    private static boolean testIfPrimeEfficient(List<Integer> primes, Integer element){
        return primes.stream()
                .noneMatch(index -> element % index == 0);
    }
}
