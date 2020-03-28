package mutation;

import entities.PathChromosome;

/**
 * Solo muta si al otener un número al azar este es menor que la probabilidad de mutación.
 */
public interface MutationAlgorithm {
    PathChromosome mutate(PathChromosome chromosome);
}
