package mutationalgorithm.floatMutation;

import entities.BinaryChromosome;
import entities.FloatChromosome;

public interface FloatMutationAlgorithm {
	FloatChromosome mutate(FloatChromosome chromosome, double mutationProbability, double min, double max);
}
