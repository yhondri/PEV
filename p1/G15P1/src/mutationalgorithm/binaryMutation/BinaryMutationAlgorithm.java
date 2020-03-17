package mutationalgorithm.binaryMutation;

import entities.BinaryChromosome;

public interface BinaryMutationAlgorithm {
	BinaryChromosome mutate(BinaryChromosome chromosome, double mutationProbability);
}
