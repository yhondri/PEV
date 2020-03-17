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

public class Problem2 extends BinaryProblem {

	private double tolerance = 0.001;
	private double min = -10;
	private double max = 10;
	private int longitud = Utils.getGenotypeLength(min, max, tolerance) * 2;

	public Problem2(Configuration configuration, SelectionAlgorithm selectionAlgorithm, BinaryCrossoverAlgorithm binaryCrossoverAlgorithm, BinaryMutationAlgorithm mutationAlgorithm, Delegate delegate) {
		super(configuration, selectionAlgorithm, binaryCrossoverAlgorithm, mutationAlgorithm, delegate);
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
	protected boolean isBetterFitness(double absoluteBest, double absBest) {
		return absoluteBest < absBest;
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
		fenotipo = calcHolder(parsedVal[0], parsedVal[1]);
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
				ByteOps.parseBitStream(spl[0], min, max, getLongitud() / 2),
				ByteOps.parseBitStream(spl[1], min, max, getLongitud() / 2)};
	}

	public static double calcHolder(double x, double y) {
		return -Math.abs(Math.sin(x)
				* Math.cos(y)
				* Math.exp(Math.abs(1 - (Math.sqrt(x * x + y * y) / Math.PI))));
	}
	
	
	private int getLongitud() {
		return longitud;
	}

}
