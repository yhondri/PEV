package mutationalgorithm;

import entities.Chromosome;

public interface MutationAlgorithm {
    Chromosome mutate(boolean[] chromosome, double mutationProbability);
}
