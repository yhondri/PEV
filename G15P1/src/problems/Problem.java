package problems;

import base.Pair;
import crossoveralgorithm.CrossoverAlgorithm;
import entities.Chromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.MutationAlgorithm;
import selection.SelectionAlgorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Problem extends Thread {
/**
    private int numGenerations = 100;
    private int populationSize = 100;
    private double mutationProbability = 0.05;
    private double crossoverProbability = 0.6;
    private double elitePercent = 0.02; //2%
    */

    private Random rand = new Random();
    private CrossoverAlgorithm crossoverAlgorithm;
    private MutationAlgorithm mutationAlgorithm;
    protected final Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private Delegate delegate;

    public interface Delegate {
        void didEvaluateGeneration(int generation, Solution solution);
    }

    public Problem(Configuration configuration, SelectionAlgorithm selectionAlgorithm, CrossoverAlgorithm crossoverAlgorithm, MutationAlgorithm mutationAlgorithm, Delegate delegate) {
        this.configuration = configuration;
        this.selectionAlgorithm = selectionAlgorithm;
        this.crossoverAlgorithm = crossoverAlgorithm;
        this.mutationAlgorithm = mutationAlgorithm;
        this.delegate = delegate;
    }

    @Override
    public void run() {
        List<Chromosome> population = getInitialPopulation();
        List<Solution> solutions = new ArrayList<>();
        Solution solution = evaluatePopulation(population);
        solution.setAbsoluteBest(solution.getBestFitness());
        solutions.add(solution);
        delegate.didEvaluateGeneration(0, solution);
        double absBest = solution.getAbsoluteBest();
        for (int i = 1; i < configuration.getNumberOfGenerations(); i++) {
            List<Chromosome> eliteList = getElite(population);
            population = selectionAlgorithm.selectPopulation(population);
            crossPopulation(population);
            mutatePopulation(population);
            addElite(population, eliteList);
            solution = evaluatePopulation(population);
            absBest = compareBest(solution, absBest);
            solution.setAbsoluteBest(absBest);
            solutions.add(solution);

            delegate.didEvaluateGeneration(i, solution);
        }
    }

    abstract protected Chromosome getRandomChromosome();
    abstract protected void sortPopulation(List<Chromosome> population);
    abstract protected double getFitness(Chromosome chromosome);
    abstract protected double compareBest(Solution solution, double absBest);

    private List<Chromosome> getInitialPopulation() {
        List<Chromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            Chromosome newChromosome = getRandomChromosome();
            chromosomeList.add(newChromosome);
        }
        return chromosomeList;
    }

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

        sortPopulation(population);
        Chromosome bestChromosome = population.get(population.size()-1);
        solution.setAverageFitness(totalFitness/configuration.getPopulationSize());
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
        List<Integer> selectedForCrossoverList = new ArrayList<>();

        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            double crossoverResult = rand.nextDouble();
            if (crossoverResult < configuration.getCrossoverValue()) {
                selectedForCrossoverList.add(i);
            }
        }

        if (selectedForCrossoverList.size()%2 == 1) {
            int randomPosition = rand.nextInt(selectedForCrossoverList.size());
            selectedForCrossoverList.remove(randomPosition);
        }

        for (int i = 0; i < selectedForCrossoverList.size(); i += 2) {
            int position1 = selectedForCrossoverList.get(i);
            int position2 = selectedForCrossoverList.get(i+1);

            Chromosome chromosomeA = population.get(position1);
            Chromosome chromosomeB =  population.get(position2);
            Pair<Chromosome, Chromosome> result = crossoverAlgorithm.crossOver(chromosomeA, chromosomeB);
            population.set(position1, result.getElement0());
            population.set(position2, result.getElement1());
        }
    }

    private void mutatePopulation(List<Chromosome> population) {
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            Chromosome chromosome = population.get(i);
            mutationAlgorithm.mutate(chromosome, configuration.getMutationValue());
            population.set(i, chromosome);
        }
    }

    private List<Chromosome> getElite(List<Chromosome> population) {
        int eliteLength = (int)Math.ceil(population.size() * configuration.getEliteValue());
        if (eliteLength == 0) {
            return null;
        }

        sortPopulation(population);
        List<Chromosome> eliteList = new ArrayList<>(eliteLength);
        for (int i = (population.size() - 1), j = 0; i >= 0 &&  j < eliteLength ; i--, j++) {
            Chromosome newCopy = population.get(i).getCopy();
            eliteList.add(newCopy);
        }

        return eliteList;
    }

    private void addElite(List<Chromosome> population, List<Chromosome> eliteList) {
        if (eliteList == null) {
            return;
        }

        population.subList(0, eliteList.size()).clear();
        population.addAll(eliteList);
    }
}
