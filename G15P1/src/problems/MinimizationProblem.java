package problems;

import crossoveralgorithm.CrossoverAlgorithm;
import entities.Chromosome;
import entities.Configuration;
import mutationalgorithm.MutationAlgorithm;
import selection.SelectionAlgorithm;

public class MinimizationProblem extends Problem {
    public MinimizationProblem(Configuration configuration, SelectionAlgorithm selectionAlgorithm, CrossoverAlgorithm crossoverAlgorithm, MutationAlgorithm mutationAlgorithm, Delegate delegate) {
        super(configuration, selectionAlgorithm, crossoverAlgorithm, mutationAlgorithm, delegate);
    }

    public int compareTo(Chromosome a, Chromosome b) {
        return Double.compare(b.getFitness(), a.getFitness());
    }

    @Override
    protected Chromosome getRandomChromosome() {
        return null;
    }

    @Override
    protected double getFitness(Chromosome chromosome) {
        return 0;
    }
}
