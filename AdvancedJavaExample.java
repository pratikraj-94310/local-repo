package localrepo;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class AdvancedJavaExample {
    
    // This is the main method that demonstrates the advanced Java code.
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int numberOfElements = 1_000_000; // Large number of elements
        List<Integer> numbers = generateRandomNumbers(numberOfElements);

        // ExecutorService to manage a pool of threads
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            // Use a Future to get results from the Callable tasks
            Future<List<Integer>> futureResult = executor.submit(() -> {
                return numbers.parallelStream() // Parallel stream for concurrent processing
                        .filter(n -> n % 2 == 0) // Filter even numbers
                        .map(n -> n * 2) // Transform each number
                        .sorted() // Sort the results
                        .collect(Collectors.toList()); // Collect the results into a List
            });

            // Get the result from the future
            List<Integer> result = futureResult.get();

            // Print some statistics
            System.out.printf("Processed %d numbers, sample results: %s%n", result.size(), result.subList(0, Math.min(10, result.size())));

        } finally {
            // Shut down the executor service
            executor.shutdown();
        }
    }

    // Method to generate a list of random numbers
    private static List<Integer> generateRandomNumbers(int count) {
        Random random = new Random();
        return IntStream.range(0, count)
                .mapToObj(i -> random.nextInt(1000)) // Generate numbers between 0 and 999
                .collect(Collectors.toList());
    }
}