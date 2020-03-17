package mutation;

import entities.PathChromosome;

public interface MutationAlgorithm {
    PathChromosome mutate(PathChromosome chromosome, double mutationProbability);
}
