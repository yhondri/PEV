package problems.floatProblems;

import base.Utils;
import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import crossoveralgorithm.floatCrossover.FloatCrossoverAlgorithm;
import entities.BinaryChromosome;
import entities.Configuration;
import entities.FloatChromosome;
import entities.Solution;
import mutationalgorithm.binaryMutation.BinaryMutationAlgorithm;
import mutationalgorithm.floatMutation.FloatMutationAlgorithm;
import problems.FloatProblem;
import selection.SelectionAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Problem4Float extends FloatProblem {
    double min = 0;
    double max = Math.PI;
    private int geneLength;

    public Problem4Float(Configuration configuration, SelectionAlgorithm selectionAlgorithm, FloatCrossoverAlgorithm crossoverAlgorithm, FloatMutationAlgorithm mutationAlgorithm, Delegate delegate) {
        super(configuration, selectionAlgorithm, crossoverAlgorithm, mutationAlgorithm, delegate);
        geneLength = configuration.getnValue();
    }

    @Override
    protected FloatChromosome getRandomChromosome() {
        Random random = new Random();
        FloatChromosome chromosome = new FloatChromosome();
        double[] chromosomeArray = new double[geneLength];
        for (int i = 0; i < chromosomeArray.length; i++) {
            chromosomeArray[i] = min + random.nextDouble() * (max - min);
        }

        chromosome.setGenes(chromosomeArray);

        return chromosome;
    }


    @Override
    protected boolean isBetterFitness(double absoluteBest, double absBest) {
        return absoluteBest < absBest;
    }


    @Override
    protected void sortPopulation(List<FloatChromosome> population) {
        Collections.sort(population, Collections.reverseOrder());
    }

    public double compareBest(Solution solution, double absBest) {
        return Math.min(solution.getBestFitness(), absBest);
    }

    @Override
    protected double getFitness(FloatChromosome chromosome) {
        List<Double> phenotypeList = getPhenotype(chromosome.getGenes());
        double result = 0;
        for (int i = 1; i <= configuration.getnValue(); i++) {
            double value = phenotypeList.get(i - 1);
            double parentesis = Math.sin(((i + 1) * Math.pow(value, 2)) / Math.PI);
            result += Math.sin(value) * Math.pow(parentesis, 20);
        }

        return -result;
    }


    @Override
    public String getPhenotypeRepresentation(FloatChromosome chromosome) {
        List<Double> phenotypeList = getPhenotype(chromosome.getGenes());
        return phenotypeList.toString();
    }

    @Override
    protected double getMin() {
        return min;
    }

    @Override
    protected double getMax() {
        return max;
    }

    private List<Double> getPhenotype(double[] chromosome) {
        List<Double> phenotype = new ArrayList<>(configuration.getnValue());
        for (int i = 0; i < configuration.getnValue(); i++) {
            phenotype.add(chromosome[i]);
        }
        return phenotype;
    }
}
