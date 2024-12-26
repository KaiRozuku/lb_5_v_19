import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Task1 {
    public static void main(String[] args) {
        Random random = new Random();
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int result = random.nextInt(1, 101);
            System.out.println("Future1 завершено: " + result);
            return result;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int result = random.nextInt(1, 101);
            System.out.println("Future2 завершено: " + result);
            return result;
        });

        CompletableFuture<Integer> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            System.out.println("Об'єднання результатів: " + result1 + " + " + result2);
            return result1 + result2;
        });

        try {
            System.out.println("Загальний результат: " + combinedFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }

        CompletableFuture<Integer> futureThenCompose = future1.thenCompose(result1 -> CompletableFuture.supplyAsync(() -> {
            int processed = result1 * 2;
            System.out.println("Результат після thenCompose: " + processed);
            return processed;
        }));

        try {
            System.out.println("Результат thenCompose: " + futureThenCompose.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }

        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2);
        try {
            System.out.println("Результат anyOf: " + anyOfFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(future1, future2);
        try {
            allOfFuture.get();
            System.out.println("Усі завдання завершені.");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException();
        }
    }
}
