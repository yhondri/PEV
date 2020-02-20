import base.Pair;
import crossoveralgorithm.CrossoverAlgorithm;
import crossoveralgorithm.SinglePointCrossover;
import entities.Chromosome;
import entities.Solution;
import mutationalgorithm.BasicMutation;
import mutationalgorithm.MutationAlgorithm;
import problems.GenericProblem;
import problems.Problem1;
import selection.RouletteSelection;
import selection.SelectionAlgorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Problem {

    private int numGenerations = 100;
    private int populationSize = 100;
    private double mutationProbability = 0.05;
    private Random rand = new Random();
    private GenericProblem problem;
    private CrossoverAlgorithm crossoverAlgorithm;
    private MutationAlgorithm mutationAlgorithm;

    void run() {
        problem = new Problem1();
        SelectionAlgorithm selectionAlgorithm = new RouletteSelection();
        crossoverAlgorithm = new SinglePointCrossover(rand);
        mutationAlgorithm = new BasicMutation();

        List<Chromosome> population = getInitialPopulation();
        List<Solution> solutions = new ArrayList<>();
        Solution solution = evaluatePopulation(population);
        solutions.add(solution);

        for (int i = 1; i < numGenerations; i++) {
            population = selectionAlgorithm.selectPopulation(population);
            crossPopulation(population);
            mutatePopulation(population);

            solution = evaluatePopulation(population);
            solutions.add(solution);
        }

        Collections.sort(solutions);

        int parada = 0;
        parada += 1;
    }

    private List<Chromosome>  getInitialPopulation() {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome newChromosome = problem.getRandomChromosome();
            chromosomeList.add(newChromosome);
        }
        return chromosomeList;
    }

    private Solution evaluatePopulation(List<Chromosome> population) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return solution;
        }

        double totalFitness = 0;

        for (Chromosome chromosome : population) {
            double fitness = problem.getFitness(chromosome);
            chromosome.setFitness(fitness);

            totalFitness += fitness;
        }

        Collections.sort(population);
        int index = population.size()-1;
        Chromosome bestChromosome = population.get(population.size()-1);
        solution.setAverageFitness(totalFitness/populationSize);
        solution.setBestFitness(bestChromosome.getFitness());
        solution.setWorstFitness(population.get(0).getFitness());

        double acumulatedFitness = 0;
        //Calculamos el fitness acumulado - Muestreo estocástico - Tema02 - Pág 36
        //Puede cambiar para mínimos :s
        for (int i = population.size()-1; i >= 0; i--) {
            acumulatedFitness += population.get(i).getFitness()/totalFitness;
            population.get(i).setAcumulatedFitness(acumulatedFitness);
        }

        return solution;
    }

    private void crossPopulation(List<Chromosome> population) {
        for (int i = 0; i < populationSize; i+=2) {
            Chromosome chromosomeA = population.get(i);
            Chromosome chromosomeB = population.get(i+1);
            Pair<Chromosome, Chromosome> result = crossoverAlgorithm.crossOver(chromosomeA, chromosomeB, mutationProbability);
            population.set(i, result.getElement0());
            population.set(i+1, result.getElement1());
        }
    }

    private void mutatePopulation(List<Chromosome> population) {
        for (int i = 0; i < populationSize; i++) {
            Chromosome chromosome = population.get(i);
            mutationAlgorithm.mutate(chromosome, mutationProbability);
            population.set(i, chromosome);
        }
    }
}
