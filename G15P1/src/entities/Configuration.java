package entities;

import crossoveralgorithm.CrossoverAlgorithm;
import mutationalgorithm.MutationAlgorithm;
import selection.SelectionAlgorithm;

public class Configuration {
    private int populationSize;
    private int numberOfGenerations;
    private double crossoverValue;
    private double mutationValue;
    private double eliteValue;
    private final double tolerance = 0.001;
    private int nValue;

    public Configuration(int populationSize, int numberOfGenerations, double crossoverValue, double mutationValue, double eliteValue, int nValue) {
        this.populationSize = populationSize;
        this.numberOfGenerations = numberOfGenerations;
        this.crossoverValue = crossoverValue;
        this.mutationValue = mutationValue;
        this.eliteValue = eliteValue;
        this.nValue = nValue;
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

    public double getCrossoverValue() {
        return crossoverValue;
    }

    public void setCrossoverValue(double crossoverValue) {
        this.crossoverValue = crossoverValue;
    }

    public double getMutationValue() {
        return mutationValue;
    }

    public void setMutationValue(double mutationValue) {
        this.mutationValue = mutationValue;
    }

    public double getEliteValue() {
        return eliteValue;
    }

    public void setEliteValue(double eliteValue) {
        this.eliteValue = eliteValue;
    }

    public double getTolerance() {
        return tolerance;
    }

    public int getnValue() {
        return nValue;
    }
}
