package entities;

public class Configuration {
    private String dataFileName;
    private int chromosomeSize;
    private int populationSize;
    private int numberOfGenerations;
    private double crossoverValue;
    private double mutationValue;
    private double eliteValue;
    private final double tolerance = 0.0001;

    public Configuration(String dataFileName, int chromosomeSize, int populationSize, int numberOfGenerations, double crossoverValue, double mutationValue, double eliteValue) {
        this.dataFileName = dataFileName;
        this.chromosomeSize = chromosomeSize;
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.crossoverValue = crossoverValue;
        this.mutationValue = mutationValue;
        this.eliteValue = eliteValue;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public int getChromosomeSize() {
        return chromosomeSize;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public int getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public double getCrossoverValue() {
        return crossoverValue;
    }

    public double getMutationValue() {
        return mutationValue;
    }

    public double getEliteValue() {
        return eliteValue;
    }
}
