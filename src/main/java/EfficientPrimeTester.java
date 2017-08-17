import java.util.List;

public class EfficientPrimeTester implements IPrimeTester{
    private List<Integer> primes;

    @Override
    public void setPrimes(List<Integer> primes) {
        this.primes = primes;
    }

    @Override
    public boolean test(int element) {
        return primes.stream()
                .noneMatch(index -> element % index == 0);
    }
}
