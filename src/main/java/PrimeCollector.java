import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

public class PrimeCollector implements Collector<Integer,
        Map<Boolean, List<Integer>>,
        Map<Boolean, List<Integer>>> {

    private IPrimeTester primeTesterStrategy;

    private PrimeCollector(){}

    public PrimeCollector setPrimeTester(IPrimeTester primeTesterImpl){
        this.primeTesterStrategy = primeTesterImpl;
        return this;
    }

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
            this.primeTesterStrategy.setPrimes(accumulator.get(true));
            accumulator.get(this.primeTesterStrategy.test(element))
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

    public static class PrimeCollectorBuilder {

        private IPrimeTester primeTester;

        public PrimeCollectorBuilder(){}

        public PrimeCollectorBuilder withStrategy(IPrimeTester tester){
            this.primeTester = tester;
            return this;
        }

        public PrimeCollector build(){
            return new PrimeCollector()
                    .setPrimeTester(primeTester);
        }
    }
}
