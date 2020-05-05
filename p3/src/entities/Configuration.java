package entities;

//import javafx.util.Pair;

import helper.Pair;

import java.util.List;

public class Configuration {
    private int chromosomeSize;
    private int populationSize;
    private int numberOfGenerations;
    private double crossoverValue;
    private double mutationValue;
    private double eliteValue;
    private int maxDepth;
    private List<Pair<String, Integer>> functions;
    private List<String> terminalList;
    private MultiplexorTestValue multiplexorTestValue;

    public Configuration(int populationSize, int numberOfGenerations) {
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
    }

    public Configuration(List<Pair<String, Integer>> functions, List<String> terminalList, int chromosomeSize, int populationSize, int numberOfGenerations, double crossoverValue, double mutationValue, double eliteValue, int maxDepth, MultiplexorTestValue multiplexorTestValue) {
        this.functions = functions;
        this.terminalList = terminalList;
        this.chromosomeSize = chromosomeSize;
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.crossoverValue = crossoverValue;
        this.mutationValue = mutationValue;
        this.eliteValue = eliteValue;
        this.maxDepth = maxDepth;
        this.multiplexorTestValue = multiplexorTestValue;
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

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getNumberOfFunctions() {
        return functions.size();
    }

    public Pair<String, Integer> getFunctionAtIndex(int index) {
        return functions.get(index);
    }

    public int getNumberOfTerminals() {
        return terminalList.size();
    }

    public String getTerminalAtIndex(int index) {
        return terminalList.get(index);
    }

    public MultiplexorTestValue getMultiplexorTestValue() {
        return multiplexorTestValue;
    }
}
