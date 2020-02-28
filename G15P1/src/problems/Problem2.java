package problems;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import base.Utils;
import crossoveralgorithm.CrossoverAlgorithm;
import entities.Chromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.MutationAlgorithm;
import selection.SelectionAlgorithm;
import utils.ByteOps;

public class Problem2 extends MinimizationProblem {

	private double tolerance = 0.001;
	private double min = -10;
	private double max = 10;
	private int longitud = Utils.getGenotypeLength(min, max, tolerance) * 2;

	public Problem2(Configuration configuration, SelectionAlgorithm selectionAlgorithm, CrossoverAlgorithm crossoverAlgorithm, MutationAlgorithm mutationAlgorithm, Delegate delegate) {
		super(configuration, selectionAlgorithm, crossoverAlgorithm, mutationAlgorithm, delegate);
	}

	@Override
	 public Chromosome getRandomChromosome() {
        Chromosome chromosome = new Chromosome();
        boolean[] chromosomeArray = new boolean[longitud];
        Random random = new Random();

        for (int i = 0; i < chromosomeArray.length; i++) {
            chromosomeArray[i] = random.nextBoolean();
        }

        chromosome.setGenes(chromosomeArray);

        return chromosome;
    }

	@Override
	protected void sortPopulation(List<Chromosome> population) {
		Collections.sort(population, Collections.reverseOrder());
	}

	public double compareBest(Solution solution, double absBest) {
		return Math.min(solution.getBestFitness(), absBest);
	}

	@Override
	public double getFitness(Chromosome chromosome) {
		double parsedVal[] = decode(chromosome), fenotipo;
		fenotipo = calcHolder(parsedVal[0], parsedVal[1]);
		return fenotipo;
	}
	
	
	public double[] decode(Chromosome chromosome) {
		boolean spl[][] = ByteOps.splitBitStream(chromosome.getGenes());
		return new double[]{
				ByteOps.parseBitStream(spl[0], min, max, getLongitud()/2),
				ByteOps.parseBitStream(spl[1], min, max, getLongitud()/2)};
	}
	
	public static double calcHolder(double x, double y) {
		return -Math.abs(Math.sin(x)
				*Math.cos(y)
				*Math.exp(Math.abs(1 - (Math.sqrt(x*x + y*y)/Math.PI))));
	}
	
	
	private int getLongitud() {
		return longitud;
	}

}
