import java.util.List;
import java.util.stream.IntStream;

public class BasicPrimeTester implements IPrimeTester{
    private final static int RANGE_START = 2;

    @Override
    public boolean test(int element) {
        return IntStream.rangeClosed(RANGE_START, (int) Math.sqrt(element))
                .boxed()
                .noneMatch(index -> element % index == 0);
    }

    @Override
    public void setPrimes(List<Integer> primes) {
        return ;
    }
}
