package entities;

public class Solution implements Comparable<Solution> {
    private double bestFitness;
    private double worstFitness;
    private double averageFitness;

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

}