package cs06;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClassifierAlpha {

    private final List<Integer> numbers;

    public ClassifierAlpha(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public List<String> classifier() {
        return numbers.stream().map(this::getString).collect(Collectors.toList());
    }

    private String getString(Integer s) {
        String str = s + " : "
                + isPerfect(s)
                + isAbundant(s)
                + isDeficient(s)
                + isPrime(s)
                + isSquared(s);
        return str.substring(0, str.length() - 2);
    }

    public boolean isFactor(int potentialFactor, int number) {
        return number % potentialFactor == 0;
    }

    public static int sum(Set factors) {
        return (int) factors.stream().reduce(0, (a, b) -> (int) a + (int) b);
    }

    public Set factors(int number) {
        HashSet factors = new HashSet<>();
        for (int pod = 1; pod <= Math.sqrt(number); pod++) {
            if (isFactor(pod, number)) {
                factors.add(pod);
                factors.add(number / pod);
            }
        }
        return factors;
    }

    public String isPrime(int number) {
        Set primeSet = new HashSet<>() {
            {
                add(1);
                add(number);
            }
        };
        return number > 1 && factors(number).equals(primeSet) ? "prime, " : "";
    }

    public String isPerfect(int number) {
        return sum(factors(number)) - number == number ? "perfect, " : "";
    }

    public String isAbundant(int number) {
        return sum(factors(number)) - number > number ? "abundant, " : "";
    }

    public String isDeficient(int number) {
        return sum(factors(number)) - number < number ? "deficient, " : "";
    }

    public String isSquared(int number) {
        return factors(number).stream()
                .map(String::valueOf)
                .mapToDouble(s -> Double.parseDouble((String) s))
                .boxed()
                .collect(Collectors.toList())
                .contains(Math.sqrt(number)) ? "squared, " : "";

    }
}