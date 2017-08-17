import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimeCollectorTest {
    private final static int START_INDEX = 2;
    private final static int TARGET_100 = 100;
    private final static int TARGET_1000 = 1000;
    private final static int TARGET_10000 = 10000;
    private final static int TARGET_100000 = 100000;
    private static Stream<Integer> integerStream;
    private String line;

    Map<Integer, List<Integer>> testSampleMap = new HashMap<Integer, List<Integer>>(){{
        put(TARGET_100, Arrays.asList(new Integer[]{2, 23, 41, 43, 53, 59, 67, 79, 89, 97}));
        put(TARGET_1000, Arrays.asList(new Integer[]{2, 71, 127, 223, 307, 401, 503, 727, 859, 977}));
        put(TARGET_10000, Arrays.asList(new Integer[]{2, 1031, 2131, 3253, 4789, 5869, 6211, 7417, 8273, 9547}));
        put(TARGET_100000, Arrays.asList(new Integer[]{64283, 46861, 95629, 72431, 42577, 69263, 22027, 85363, 97441, 4733}));
    }};

    @BeforeAll
    public static void readPrimesAsArrays(){
    }

    @ParameterizedTest(name = "run test #{index} with argument {arguments}")
    @ValueSource(ints = {TARGET_100, TARGET_1000, TARGET_10000, TARGET_100000})
    void testGenerationWithBasicStrategy(int upperValue){
        long startTime = System.currentTimeMillis();
        Map<Boolean, List<Integer>> mapOfPrimes =
                        IntStream.range(START_INDEX, upperValue)
                        .boxed()
                        .collect(new PrimeCollector.PrimeCollectorBuilder()
                                .withStrategy(new BasicPrimeTester())
                                .build());

        long endTime = System.currentTimeMillis();

        Assertions.assertTrue(mapOfPrimes.get(true).containsAll(testSampleMap.get(upperValue)),
                () -> "Issue with generating the prime numbers up to " + upperValue + " !");

        Assertions.assertFalse(mapOfPrimes.get(false).containsAll(testSampleMap.get(upperValue)),
                () -> "Issue with generating the non prime numbers up to " + upperValue + " !");

        System.out.println("Time for basic strategy on " + upperValue + " items = " + (endTime - startTime) + " miliseconds");
    }

    @ParameterizedTest(name = "run test #{index} with argument {arguments}")
    @ValueSource(ints = {TARGET_100, TARGET_1000, TARGET_10000, TARGET_100000})
    public void testGenerationWithEfficientStrategy(int upperValue) {
        long startTime = System.currentTimeMillis();
        Map<Boolean, List<Integer>> mapOfPrimes =
                IntStream.range(START_INDEX, upperValue)
                        .boxed()
                        .collect(new PrimeCollector.PrimeCollectorBuilder()
                                .withStrategy(new EfficientPrimeTester())
                                .build());
        long endTime = System.currentTimeMillis();

        Assertions.assertTrue(mapOfPrimes.get(true).containsAll(testSampleMap.get(upperValue)),
                () -> "Issue with generating the prime numbers up to " + upperValue + " !");

        Assertions.assertFalse(mapOfPrimes.get(false).containsAll(testSampleMap.get(upperValue)),
                () -> "Issue with generating the non prime numbers up to " + upperValue + " !");

        System.out.println("Time for efficient strategy on " + upperValue + " items = " + (endTime - startTime) + " miliseconds");
    }
}
