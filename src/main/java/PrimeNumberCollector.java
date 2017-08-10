import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by Radu on 8/10/2017.
 */
public class PrimeNumberCollector {
    private final static int START_INDEX = 2;
    private final static int TARGET = 100;

    public static void main(String[] args) {
        Map<Boolean, List<Integer>> mapPrimeNonPrime =
                IntStream.range(START_INDEX, TARGET)
                .boxed()
                .collect(new PrimeCollector());

        System.out.println(mapPrimeNonPrime.get(true));
        return ;
    }
}
