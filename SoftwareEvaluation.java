public class SoftwareEvaluation {
    private final String name;
    private final int price;
    private double score;
    private int supportScore;
    private int reviewScore;

    public SoftwareEvaluation(String name, int price, double score) {
        this.name = name;
        this.price = price;
        this.score = score;
    }

    public void setSupportScore(int supportScore) {
        this.supportScore = supportScore;
    }

    public void addReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }

    public void calculateFinalScore() {
        this.score += supportScore * 0.1 + reviewScore * 0.2;
    }

    public double getFinalScore() {
        return score;
    }

    @Override
    public String toString() {
        return name + " [Price: " + price + ", Final Score: " + String.format("%.2f", score) + "]";
    }
}