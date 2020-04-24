package entities;

import javafx.util.Pair;

import java.util.List;

public class Configuration {
    private int chromosomeSize;
    private int populationSize;
    private int numberOfGenerations;
    private double crossoverValue;
    private double mutationValue;
    private double eliteValue;
    private String costeOptimo;
    private int maxDepth;
    private List<Pair<String, Integer>> functions;

    public Configuration(List<Pair<String, Integer>> functions, int chromosomeSize, int populationSize, int numberOfGenerations, double crossoverValue, double mutationValue, double eliteValue, int maxDepth) {
        this.functions = functions;
        this.chromosomeSize = chromosomeSize;
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.crossoverValue = crossoverValue;
        this.mutationValue = mutationValue;
        this.eliteValue = eliteValue;
        this.costeOptimo = costeOptimo;
        this.maxDepth = maxDepth;
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
