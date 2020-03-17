package crossoveralgorithm.binaryCrossover;

import base.Pair;
import entities.BinaryChromosome;

import java.util.Random;

public class UniformBinaryCrossover implements BinaryCrossoverAlgorithm {

	private double prob = 0.5;

	public UniformBinaryCrossover(double prob) {
		this.prob = prob;
	}

	@Override
	public Pair<BinaryChromosome, BinaryChromosome> crossOver(BinaryChromosome chromosomeA, BinaryChromosome chromosomeB) {
		Random rng = new Random();
		Pair<BinaryChromosome, BinaryChromosome> descendants = new Pair<>(chromosomeA.getCopy(), chromosomeB.getCopy());
		boolean[] genesA = chromosomeA.getGenes().clone(), genesB = chromosomeB.getGenes().clone();
		boolean geneAtI;
		for (int i = 0; i < chromosomeA.getGenesLength(); i++) {
			if (rng.nextDouble() < prob) {
				geneAtI = genesA[i];
				genesA[i] = genesB[i];
				genesB[i] = geneAtI;
			}
        }
        descendants.getElement0().setGenes(genesA);
        descendants.getElement1().setGenes(genesB);
        return descendants;
    }
}
