import base.Pair;
import base.Utils;
import crossoveralgorithm.CrossoverAlgorithm;
import crossoveralgorithm.SinglePointCrossover;
import entities.Chromosome;
import mutationalgorithm.BasicMutation;
import mutationalgorithm.MutationAlgorithm;
import problems.GenericProblem;
import problems.Problem1;
import java.util.Random;

public class Problem {

    private int numGenerations = 100;
    private double mutationProbability = 0.05;
    private Random rand = new Random();

    void run() {
        double minX1 = -3, minX2 = 4.1, maxX1 = 12.1, maxX2 = 5.8, tolerance = 0.001;
        Chromosome chromosomeA = getRandomChromosome(minX1, maxX1, tolerance);
        Chromosome chromosomeB = getRandomChromosome(minX2, maxX2, tolerance);

        CrossoverAlgorithm crossoverAlgorithm = new SinglePointCrossover(rand);
        Pair<boolean[], boolean[]> decentsPair = crossoverAlgorithm.crossOver(chromosomeA.getGenes(), chromosomeB.getGenes(), 0.6);

        MutationAlgorithm basicMutation = new BasicMutation();
        Chromosome descentA = basicMutation.mutate(decentsPair.getElement0(), mutationProbability);
        Chromosome descentB = basicMutation.mutate(decentsPair.getElement1(), mutationProbability);

        GenericProblem problem = new Problem1();
        double fitness = problem.evaluate(descentA.getGenes(), descentB.getGenes());

        for (int i = 1; i < numGenerations; i++) {

        }
    }

    private Chromosome getRandomChromosome(double min, double max, double tolerance) {
        Chromosome chromosome = new Chromosome();

        int genesLength = Utils.getGenotypeLength(min, max, tolerance);
        chromosome.setGenesLength(genesLength);
        boolean[] chromosomeArray = new boolean[chromosome.getGenesLength()];

        for (int i = 0; i < chromosome.getGenesLength(); i++) {
            chromosomeArray[i] = rand.nextBoolean();
        }

        chromosome.setGenes(chromosomeArray);

        return chromosome;
    }
}
