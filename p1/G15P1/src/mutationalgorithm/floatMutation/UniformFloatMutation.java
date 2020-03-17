package mutationalgorithm.floatMutation;

import entities.FloatChromosome;

import java.util.Random;

public class UniformFloatMutation implements FloatMutationAlgorithm {
	@Override
	public FloatChromosome mutate(FloatChromosome chromosome, double mutationProbability, double min, double max) {
		double[] newGenes = new double[chromosome.getGenesLength()];
		Random random = new Random();
		for (int i = 0; i < chromosome.getGenesLength(); i++) {
			double result = random.nextDouble();
			newGenes[i] = (result < mutationProbability) ? (min + random.nextDouble() * (max - min)) : chromosome.getGenes()[i];
		}

		chromosome.setGenes(newGenes);
		return chromosome;
	}
}
