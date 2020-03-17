package crossoveralgorithm.floatCrossover;

import base.Pair;
import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import entities.BinaryChromosome;
import entities.FloatChromosome;

import java.util.Random;

public class SinglePointFloatCrossover implements FloatCrossoverAlgorithm {
	private Random random;

	public SinglePointFloatCrossover(Random random) {
		this.random = random;
	}

	@Override
	public Pair<FloatChromosome, FloatChromosome> crossOver(FloatChromosome chromosomeA, FloatChromosome chromosomeB) {
		int chromosomeSize = Math.max(chromosomeA.getGenesLength(), chromosomeB.getGenesLength());

		int crossoverPoint = random.nextInt(chromosomeA.getGenesLength());

		double[] chromosomeANew = new double[chromosomeB.getGenesLength()];
		double[] chromosomeBNew = new double[chromosomeA.getGenesLength()];

		for (int i = 0; i < crossoverPoint; i++) {
			if (i < chromosomeANew.length && i < chromosomeA.getGenesLength()) {
				chromosomeANew[i] = chromosomeA.getGenes()[i];
			}

			if (i < chromosomeBNew.length && i < chromosomeB.getGenesLength()) {
				chromosomeBNew[i] = chromosomeB.getGenes()[i];
			}
		}

		for (int i = crossoverPoint; i < chromosomeSize; i++) {
			if (i < chromosomeANew.length && i < chromosomeB.getGenesLength()) {
				chromosomeANew[i] = chromosomeB.getGenes()[i];
			}

			if (i < chromosomeBNew.length && i < chromosomeA.getGenesLength()) {
				chromosomeBNew[i] = chromosomeA.getGenes()[i];
			}
		}

		return new Pair<>(new FloatChromosome(chromosomeANew), new FloatChromosome(chromosomeBNew));
	}
}
