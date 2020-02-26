package problems;

import crossoveralgorithm.CrossoverAlgorithm;
import entities.Chromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.MutationAlgorithm;
import selection.SelectionAlgorithm;

import java.util.Collections;
import java.util.List;

public abstract class MinimizationProblem extends Problem {
    public MinimizationProblem(Configuration configuration, SelectionAlgorithm selectionAlgorithm, CrossoverAlgorithm crossoverAlgorithm, MutationAlgorithm mutationAlgorithm, Delegate delegate) {
        super(configuration, selectionAlgorithm, crossoverAlgorithm, mutationAlgorithm, delegate);
    }

    public int compareTo(Chromosome a, Chromosome b) {
        return Double.compare(b.getFitness(), a.getFitness());
    }

    @Override
    public double compareBest(Solution solution, double absBest) {
        return Math.min(solution.getBestFitness(), absBest);
    }

    @Override
    public Solution evaluatePopulation(List<Chromosome> population) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return solution;
        }

        double totalFitness = 0;
        for (Chromosome chromosome : population) {
            double fitness = getFitness(chromosome);
            chromosome.setFitness(fitness);
            totalFitness += fitness;
        }

        Collections.sort(population);
        int index = population.size() - 1;
        Chromosome bestChromosome = population.get(0);
        solution.setAverageFitness(totalFitness / configuration.getPopulationSize());
        solution.setBestFitness(bestChromosome.getFitness());
        solution.setWorstFitness(population.get(index).getFitness());

        double acumulatedFitness = 0;
        //Calculamos el fitness acumulado - Muestreo estocástico - Tema02 - Pág 36
        //Puede cambiar para mínimos :s
        for (int i = population.size() - 1; i >= 0; i--) {
            acumulatedFitness += population.get(i).getFitness() / totalFitness;
            population.get(i).setAcumulatedFitness(acumulatedFitness);
        }

        return solution;
    }

    @Override
    protected abstract Chromosome getRandomChromosome();

    @Override
    protected abstract double getFitness(Chromosome chromosome);
}
