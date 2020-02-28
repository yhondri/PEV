package crossoveralgorithm.binaryCrossover;

import base.*;
import entities.BinaryChromosome;

import java.util.Random;

public class SinglePointBinaryCrossover implements BinaryCrossoverAlgorithm {
	private Random random;

	public SinglePointBinaryCrossover(Random random) {
		this.random = random;
	}

	@Override
	public Pair<BinaryChromosome, BinaryChromosome> crossOver(BinaryChromosome chromosomeA, BinaryChromosome chromosomeB) {
		int chromosomeSize = Math.max(chromosomeA.getGenesLength(), chromosomeB.getGenesLength());

		int crossoverPoint = random.nextInt(chromosomeA.getGenesLength());

		boolean[] chromosomeANew = new boolean[chromosomeB.getGenesLength()];
		boolean[] chromosomeBNew = new boolean[chromosomeA.getGenesLength()];

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

		return new Pair<>(new BinaryChromosome(chromosomeANew), new BinaryChromosome(chromosomeBNew));
    }
}
