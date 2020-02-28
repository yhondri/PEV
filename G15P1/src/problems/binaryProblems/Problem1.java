package problems.binaryProblems;

import base.Utils;
import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import entities.BinaryChromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.binaryMutation.BinaryMutationAlgorithm;
import problems.BinaryProblem;
import selection.SelectionAlgorithm;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Problem1 extends BinaryProblem {

    double minX1 = -3.0, minX2 = 4.1, maxX1 = 12.1, maxX2 = 5.8;
    private final int geneALength = Utils.getGenotypeLength(minX1, maxX1, configuration.getTolerance());
    private final int geneBLength = Utils.getGenotypeLength(minX2, maxX2, configuration.getTolerance());

    public Problem1(Configuration configuration, SelectionAlgorithm selectionAlgorithm, BinaryCrossoverAlgorithm binaryCrossoverAlgorithm, BinaryMutationAlgorithm mutationAlgorithm, Delegate delegate) {
        super(configuration, selectionAlgorithm, binaryCrossoverAlgorithm, mutationAlgorithm, delegate);
    }

    @Override
    protected boolean isBetterFitness(double absoluteBest, double absBest) {
        return absoluteBest > absBest;
    }

    @Override
    public double getFitness(BinaryChromosome chromosome) {
        double phenotypeA = getPhenotype(chromosome.getGenes(), 0, geneALength - 1, geneALength, minX1, maxX1, true);
        double phenotypeB = getPhenotype(chromosome.getGenes(), geneALength, (geneALength + geneBLength - 1), geneBLength, minX2, maxX2, false);
        //phenotypeA = 11.625;
        //phenotypeB = 5.726;
        //double result = 21.5 + phenotypeA * Math.sin(4 * Math.PI * phenotypeA) + phenotypeB * Math.sin(20 * Math.PI * phenotypeB);
        return 21.5 + phenotypeA * Math.sin(4 * Math.PI * phenotypeA) + phenotypeB * Math.sin(20 * Math.PI * phenotypeB);
    }

    double getPhenotype(boolean[] chromosome, int start, int end, int chromosomeSize, double xmin, double xmax, boolean isChromosomeA) {
        double decimalValue = Utils.decodeGene(chromosome, start, end, isChromosomeA);
        return xmin + (decimalValue * ((xmax - xmin)/(Math.pow(2, chromosomeSize) - 1)));
    }

    @Override
    public String getPhenotypeRepresentation(BinaryChromosome chromosome) {
        double phenotypeA = getPhenotype(chromosome.getGenes(), 0, geneALength - 1, geneALength, minX1, maxX1, true);
        double phenotypeB = getPhenotype(chromosome.getGenes(), geneALength, (geneALength + geneBLength - 1), geneBLength, minX2, maxX2, false);
        return String.format("[%.4f, %.4f]", phenotypeA, phenotypeB);
    }

    @Override
    public BinaryChromosome getRandomChromosome() {
        BinaryChromosome chromosome = new BinaryChromosome();
        boolean[] chromosomeArray = new boolean[geneALength + geneBLength];
        Random random = new Random();

        for (int i = 0; i < chromosomeArray.length; i++) {
            chromosomeArray[i] = random.nextBoolean();
        }

        chromosome.setGenes(chromosomeArray);

        return chromosome;
    }

    @Override
    protected void sortPopulation(List<BinaryChromosome> population) {
        Collections.sort(population);
    }

    public double compareBest(Solution solution, double absBest) {
        return Math.max(solution.getBestFitness(), absBest);
    }
}
