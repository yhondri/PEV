package entities;

public class Solution implements Comparable<Solution> {
    private double bestFitness;
    private double worstFitness;
    private double averageFitness;
    private double absoluteBest;
    private String absoluteBestRepresentation;

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }

    public double getWorstFitness() {
        return worstFitness;
    }

    public void setWorstFitness(double worstFitness) {
        this.worstFitness = worstFitness;
    }

    public double getAverageFitness() {
        return averageFitness;
    }

    public void setAverageFitness(double averageFitness) {
        this.averageFitness = averageFitness;
    }


    @Override
    public int compareTo(Solution obj) {
        return Double.compare(this.getBestFitness(), obj.getBestFitness());
    }

    public double getAbsoluteBest() {
        return absoluteBest;
    }

    public void setAbsoluteBest(double absoluteBest) {
        this.absoluteBest = absoluteBest;
    }

    public void setAbsoluteBestRepresentation(String absoluteBestRepresentation) {
        this.absoluteBestRepresentation = absoluteBestRepresentation;
    }

    public String getAbsoluteBestRepresentation() {
        return absoluteBestRepresentation;
    }
}
