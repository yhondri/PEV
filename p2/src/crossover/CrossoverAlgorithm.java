package crossover;

import entities.PathChromosome;
import helper.Pair;

public interface CrossoverAlgorithm {
    Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB);
}