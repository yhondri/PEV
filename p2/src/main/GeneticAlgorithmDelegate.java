package main;

import entities.PathChromosome;
import entities.Solution;

public interface GeneticAlgorithmDelegate {
    void didEvaluateGeneration(int generation, Solution solution);
}

