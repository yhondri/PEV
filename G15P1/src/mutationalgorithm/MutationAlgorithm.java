package mutationalgorithm;

import entities.Chromosome;

public interface MutationAlgorithm {
    Chromosome mutate(Chromosome chromosome, double mutationProbability);
}
