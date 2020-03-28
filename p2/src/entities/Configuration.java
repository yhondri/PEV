package entities;

public class Configuration {
    private String dataFileName;
    private int chromosomeSize;
    private int populationSize;
    private int numberOfGenerations;
    private double crossoverValue;
    private double mutationValue;
    private double eliteValue;
    private String costeOptimo;

    public Configuration(String dataFileName, int chromosomeSize, int populationSize, int numberOfGenerations, double crossoverValue, double mutationValue, double eliteValue, String costeOptimo) {
        this.dataFileName = dataFileName;
        this.chromosomeSize = chromosomeSize;
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.crossoverValue = crossoverValue;
        this.mutationValue = mutationValue;
        this.eliteValue = eliteValue;
        this.costeOptimo = costeOptimo;
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

    public String getCosteOptimo() {
        return costeOptimo;
    }
}
