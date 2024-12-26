import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Task2 {
    private static final Random random = new Random();
    public static void main(String[] args) {
        List<String> softwareOptions = List.of("Software A", "Software B", "Software C");

        List<CompletableFuture<SoftwareEvaluation>> evaluationFutures = softwareOptions.stream()
                .map(Task2::evaluateSoftwareAsync)
                .toList();

        CompletableFuture<Void> allEvaluations = CompletableFuture.allOf(
                evaluationFutures.toArray(new CompletableFuture[0]));

        allEvaluations.thenRun(() -> {
            List<SoftwareEvaluation> evaluations = evaluationFutures.stream()
                    .map(CompletableFuture::join)
                    .toList();

            evaluations.forEach(evaluation -> System.out.println("Initial Evaluation: " + evaluation));

            SoftwareEvaluation bestSoftware = evaluations.stream()
                    .max(Comparator.comparingDouble(SoftwareEvaluation::getFinalScore))
                    .orElseThrow();

            System.out.println("Best Software: " + bestSoftware);
        }).join();

        CompletableFuture<SoftwareEvaluation> anyOfResult = CompletableFuture.anyOf(
                        evaluationFutures.toArray(new CompletableFuture[0]))
                .thenApply(result -> (SoftwareEvaluation) result);

        SoftwareEvaluation firstCompleted = anyOfResult.join();
        System.out.println("First completed software: " + firstCompleted);
    }

    private static CompletableFuture<SoftwareEvaluation> evaluateSoftwareAsync(String softwareName) {
        CompletableFuture<Integer> priceFuture = CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            return random.nextInt(100, 500);
        });

        CompletableFuture<Integer> functionalityFuture = CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            return random.nextInt(1, 10);
        });

        CompletableFuture<Integer> supportFuture = CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            return random.nextInt(1, 10);
        });

        return priceFuture.thenCombine(functionalityFuture, (price, functionality) -> {
            double score = functionality / (price / 100.0);
            return new SoftwareEvaluation(softwareName, price, score);
        }).thenCombine(supportFuture, (evaluation, support) -> {
            evaluation.setSupportScore(support);
            evaluation.calculateFinalScore();
            return evaluation;
        }).thenCompose(Task2::fetchUserReviewAsync);
    }

    private static CompletableFuture<SoftwareEvaluation> fetchUserReviewAsync(SoftwareEvaluation evaluation) {
        return CompletableFuture.supplyAsync(() -> {
            simulateDelay();
            int reviewScore = random.nextInt(1, 10);
            evaluation.addReviewScore(reviewScore);
            evaluation.calculateFinalScore();
            return evaluation;
        });
    }

    private static void simulateDelay() {
        try {
            Thread.sleep(random.nextInt(100, 500));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
