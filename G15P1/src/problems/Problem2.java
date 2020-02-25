package problems;

import java.util.Random;

import base.Utils;
import entities.Chromosome;
import utils.ByteOps;

public class Problem2 implements GenericProblem {

	private int longitud;
	private double max;
	private double min;
	private double tolerance = 0.001;
	
	public Problem2() {
		min = -10;
		max = 10;
		longitud = Utils.getGenotypeLength(min, max, tolerance) * 2;
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
	public double getFitness(Chromosome chromosome) {
		double parsedVal[] = decode(chromosome), fenotipo;
		fenotipo = calcHolder(parsedVal[0], parsedVal[1]);
		return -fenotipo;
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