package mutationalgorithm.binaryMutation;

import entities.BinaryChromosome;

import java.util.Random;

public class BasicBinaryMutation implements BinaryMutationAlgorithm {
	@Override
	public BinaryChromosome mutate(BinaryChromosome chromosome, double mutationProbability) {
		boolean[] newGenes = new boolean[chromosome.getGenesLength()];
		Random random = new Random();
		for (int i = 0; i < chromosome.getGenesLength(); i++) {
			double result = random.nextDouble();
			newGenes[i] = (result < mutationProbability) ? !chromosome.getGenes()[i] : chromosome.getGenes()[i];
		}

		chromosome.setGenes(newGenes);
		return chromosome;
	}
}
