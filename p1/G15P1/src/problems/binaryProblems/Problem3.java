package problems.binaryProblems;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import base.Utils;
import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import entities.BinaryChromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.binaryMutation.BinaryMutationAlgorithm;
import problems.BinaryProblem;
import selection.SelectionAlgorithm;
import utils.ByteOps;

public class Problem3 extends BinaryProblem {

	private final int SUM_ITERATIONS = 5;
	private double tolerance = 0.001;
	private double min = -10;
	private double max = 10;
	private int longitud = Utils.getGenotypeLength(min, max, tolerance) * 2;

	public Problem3(Configuration configuration, SelectionAlgorithm selectionAlgorithm, BinaryCrossoverAlgorithm binaryCrossoverAlgorithm, BinaryMutationAlgorithm mutationAlgorithm, Delegate delegate) {
		super(configuration, selectionAlgorithm, binaryCrossoverAlgorithm, mutationAlgorithm, delegate);
	}


	@Override
	protected boolean isBetterFitness(double absoluteBest, double absBest) {
		return absoluteBest < absBest;
	}


	private double calcSchubert(double x1, double x2) {
		double lhs = 0, rhs = 0;
		for (int i = 1; i <= SUM_ITERATIONS; i++) {
			lhs += i * (Math.cos((i + 1) * x1 + i));
		}
		for (int i = 1; i <= SUM_ITERATIONS; i++) {
			rhs += i * (Math.cos((i + 1) * x2 + i));
		}
		return lhs * rhs;
	}

	@Override
	public BinaryChromosome getRandomChromosome() {
		BinaryChromosome chromosome = new BinaryChromosome();
		boolean[] chromosomeArray = new boolean[longitud];
		Random random = new Random();

		for (int i = 0; i < chromosomeArray.length; i++) {
			chromosomeArray[i] = random.nextBoolean();
		}

		chromosome.setGenes(chromosomeArray);

		return chromosome;
	}

	@Override
	protected void sortPopulation(List<BinaryChromosome> population) {
		Collections.sort(population, Collections.reverseOrder());
	}

	public double compareBest(Solution solution, double absBest) {
		return Math.min(solution.getBestFitness(), absBest);
	}

	@Override
	public double getFitness(BinaryChromosome chromosome) {
		double parsedVal[] = decode(chromosome), fenotipo;
		fenotipo = calcSchubert(parsedVal[0], parsedVal[1]);
		return fenotipo;
	}

	@Override
	public String getPhenotypeRepresentation(BinaryChromosome chromosome) {
		double parsedVal[] = decode(chromosome);
		return String.format("[x: %.4f, y: %.4f]", parsedVal[0], parsedVal[1]);
	}

	public double[] decode(BinaryChromosome chromosome) {
		boolean spl[][] = ByteOps.splitBitStream(chromosome.getGenes());
		return new double[]{
				ByteOps.parseBitStream(spl[0], min, max, longitud / 2),
				ByteOps.parseBitStream(spl[1], min, max, longitud / 2)};
	}
}
