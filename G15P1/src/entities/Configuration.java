package entities;

public class Configuration {
    private int populationSize;
    private int numberOfGenerations;
    private CrossoverAlgorithmType crossoverAlgorithmType;
    private double crossoverValue;
    private MutationAlgorithmType mutationAlgorithmType;
    private double mutationAlgorithmValue;
    private double eliteValue;

    public Configuration(int populationSize, int numberOfGenerations, CrossoverAlgorithmType crossoverAlgorithmType, double crossoverValue, MutationAlgorithmType mutationAlgorithmType, double mutationAlgorithmValue, double eliteValue) {
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.crossoverAlgorithmType = crossoverAlgorithmType;
        this.crossoverValue = crossoverValue;
        this.mutationAlgorithmType = mutationAlgorithmType;
        this.mutationAlgorithmValue = mutationAlgorithmValue;
        this.eliteValue = eliteValue;
    }


    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public void setNumberOfGenerations(int numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
    }

    public CrossoverAlgorithmType getCrossoverAlgorithmType() {
        return crossoverAlgorithmType;
    }

    public void setCrossoverAlgorithmType(CrossoverAlgorithmType crossoverAlgorithmType) {
        this.crossoverAlgorithmType = crossoverAlgorithmType;
    }

    public double getCrossoverValue() {
        return crossoverValue;
    }

    public void setCrossoverValue(double crossoverValue) {
        this.crossoverValue = crossoverValue;
    }

    public MutationAlgorithmType getMutationAlgorithmType() {
        return mutationAlgorithmType;
    }

    public void setMutationAlgorithmType(MutationAlgorithmType mutationAlgorithmType) {
        this.mutationAlgorithmType = mutationAlgorithmType;
    }

    public double getMutationAlgorithmValue() {
        return mutationAlgorithmValue;
    }

    public void setMutationAlgorithmValue(double mutationAlgorithmValue) {
        this.mutationAlgorithmValue = mutationAlgorithmValue;
    }

    public double getEliteValue() {
        return eliteValue;
    }

    public void setEliteValue(double eliteValue) {
        this.eliteValue = eliteValue;
    }
}
