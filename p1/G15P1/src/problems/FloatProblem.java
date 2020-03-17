package problems;

import base.Pair;
import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import crossoveralgorithm.floatCrossover.FloatCrossoverAlgorithm;
import entities.FloatChromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.binaryMutation.BinaryMutationAlgorithm;
import mutationalgorithm.floatMutation.FloatMutationAlgorithm;
import selection.SelectionAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class FloatProblem extends Thread {
    /**
     * private int numGenerations = 100;
     * private int populationSize = 100;
     * private double mutationProbability = 0.05;
     * private double crossoverProbability = 0.6;
     * private double elitePercent = 0.02; //2%
     */

    private Random rand = new Random();
    private FloatCrossoverAlgorithm crossoverAlgorithm;
    private FloatMutationAlgorithm mutationAlgorithm;
    protected final Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private Delegate delegate;

    public interface Delegate {
        void didEvaluateGeneration(int generation, Solution solution);
    }

    public FloatProblem(Configuration configuration, SelectionAlgorithm selectionAlgorithm, FloatCrossoverAlgorithm crossoverAlgorithm, FloatMutationAlgorithm mutationAlgorithm, Delegate delegate) {
        this.configuration = configuration;
        this.selectionAlgorithm = selectionAlgorithm;
        this.crossoverAlgorithm = crossoverAlgorithm;
        this.mutationAlgorithm = mutationAlgorithm;
        this.delegate = delegate;
    }

    @Override
    public void run() {
        List<FloatChromosome> population = getInitialPopulation();
        List<Solution> solutions = new ArrayList<>();
        Solution solution = evaluatePopulation(population);
        solution.setAbsoluteBest(solution.getBestFitness());
        solutions.add(solution);
        delegate.didEvaluateGeneration(0, solution);
        double absBest = solution.getAbsoluteBest();
        for (int i = 1; i < configuration.getNumberOfGenerations(); i++) {
            List<FloatChromosome> eliteList = getElite(population);
            population = selectionAlgorithm.selectPopulation(population);
            crossPopulation(population);
            mutatePopulation(population);
            addElite(population, eliteList);
            solution = evaluatePopulation(population);
            if (!isBetterFitness(solution.getAbsoluteBest(), absBest)) {
                solution.setAbsoluteBest(absBest);
            }
            absBest = solution.getAbsoluteBest();
            solutions.add(solution);

            delegate.didEvaluateGeneration(i, solution);
        }
    }

    protected abstract boolean isBetterFitness(double absoluteBest, double absBest);

    abstract protected FloatChromosome getRandomChromosome();

    abstract protected void sortPopulation(List<FloatChromosome> population);

    abstract protected double getFitness(FloatChromosome chromosome);

    abstract protected String getPhenotypeRepresentation(FloatChromosome chromosome);

    private List<FloatChromosome> getInitialPopulation() {
        List<FloatChromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            FloatChromosome newChromosome = getRandomChromosome();
            chromosomeList.add(newChromosome);
        }
        return chromosomeList;
    }

    public Solution evaluatePopulation(List<FloatChromosome> population) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return solution;
        }

        double totalFitness = 0;
        for (FloatChromosome chromosome : population) {
            double fitness = getFitness(chromosome);
            chromosome.setFitness(fitness);
            totalFitness += fitness;
        }

        sortPopulation(population);
        FloatChromosome bestChromosome = population.get(population.size() - 1);
        solution.setAverageFitness(totalFitness / configuration.getPopulationSize());
        solution.setBestFitness(bestChromosome.getFitness());
        solution.setWorstFitness(population.get(0).getFitness());
        solution.setAbsoluteBest(bestChromosome.getFitness());

        String absoluteBestRepresentation = String.format("f(X) = %s \n Fitness = %.4f", getPhenotypeRepresentation(bestChromosome), bestChromosome.getFitness());
        solution.setAbsoluteBestRepresentation(absoluteBestRepresentation);

        double acumulatedFitness = 0;
        //Calculamos el fitness acumulado - Muestreo estocástico - Tema02 - Pág 36
        //Puede cambiar para mínimos :s
        for (int i = population.size() - 1; i >= 0; i--) {
            acumulatedFitness += population.get(i).getFitness() / totalFitness;
            population.get(i).setAcumulatedFitness(acumulatedFitness);
        }

        return solution;
    }

    private void crossPopulation(List<FloatChromosome> population) {
        List<Integer> selectedForCrossoverList = new ArrayList<>();

        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            double crossoverResult = rand.nextDouble();
            if (crossoverResult < configuration.getCrossoverValue()) {
                selectedForCrossoverList.add(i);
            }
        }

        if (selectedForCrossoverList.size() % 2 == 1) {
            int randomPosition = rand.nextInt(selectedForCrossoverList.size());
            selectedForCrossoverList.remove(randomPosition);
        }

        for (int i = 0; i < selectedForCrossoverList.size(); i += 2) {
            int position1 = selectedForCrossoverList.get(i);
            int position2 = selectedForCrossoverList.get(i + 1);

            FloatChromosome chromosomeA = population.get(position1);
            FloatChromosome chromosomeB = population.get(position2);
            Pair<FloatChromosome, FloatChromosome> result = crossoverAlgorithm.crossOver(chromosomeA, chromosomeB);
            population.set(position1, result.getElement0());
            population.set(position2, result.getElement1());
        }
    }

    private void mutatePopulation(List<FloatChromosome> population) {
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            FloatChromosome chromosome = population.get(i);
            mutationAlgorithm.mutate(chromosome, configuration.getMutationValue(), getMin(), getMax());
            population.set(i, chromosome);
        }
    }

    private List<FloatChromosome> getElite(List<FloatChromosome> population) {
        int eliteLength = (int) Math.ceil(population.size() * configuration.getEliteValue());
        if (eliteLength == 0) {
            return null;
        }

        sortPopulation(population);
        List<FloatChromosome> eliteList = new ArrayList<>(eliteLength);
        for (int i = (population.size() - 1), j = 0; i >= 0 && j < eliteLength; i--, j++) {
            FloatChromosome newCopy = population.get(i).getCopy();
            eliteList.add(newCopy);
        }

        return eliteList;
    }

    private void addElite(List<FloatChromosome> population, List<FloatChromosome> eliteList) {
        if (eliteList == null) {
            return;
        }

        population.subList(0, eliteList.size()).clear();
        population.addAll(eliteList);
    }

    protected abstract double getMin();

    protected abstract double getMax();
}
