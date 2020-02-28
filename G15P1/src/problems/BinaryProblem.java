package problems;

import base.Pair;
import crossoveralgorithm.binaryCrossover.BinaryCrossoverAlgorithm;
import entities.BinaryChromosome;
import entities.Configuration;
import entities.Solution;
import mutationalgorithm.binaryMutation.BinaryMutationAlgorithm;
import selection.SelectionAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BinaryProblem extends Thread {
    /**
     * private int numGenerations = 100;
     * private int populationSize = 100;
     * private double mutationProbability = 0.05;
     * private double crossoverProbability = 0.6;
     * private double elitePercent = 0.02; //2%
     */

    private Random rand = new Random();
    private BinaryCrossoverAlgorithm binaryCrossoverAlgorithm;
    private BinaryMutationAlgorithm mutationAlgorithm;
    protected final Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private Delegate delegate;

    public interface Delegate {
        void didEvaluateGeneration(int generation, Solution solution);
    }

    public BinaryProblem(Configuration configuration, SelectionAlgorithm selectionAlgorithm, BinaryCrossoverAlgorithm binaryCrossoverAlgorithm, BinaryMutationAlgorithm mutationAlgorithm, Delegate delegate) {
        this.configuration = configuration;
        this.selectionAlgorithm = selectionAlgorithm;
        this.binaryCrossoverAlgorithm = binaryCrossoverAlgorithm;
        this.mutationAlgorithm = mutationAlgorithm;
        this.delegate = delegate;
    }

    @Override
    public void run() {
        List<BinaryChromosome> population = getInitialPopulation();
        List<Solution> solutions = new ArrayList<>();
        Solution solution = evaluatePopulation(population);
        solution.setAbsoluteBest(solution.getBestFitness());
        solutions.add(solution);
        delegate.didEvaluateGeneration(0, solution);
        double absBest = solution.getAbsoluteBest();
        for (int i = 1; i < configuration.getNumberOfGenerations(); i++) {
            List<BinaryChromosome> eliteList = getElite(population);
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

    abstract protected BinaryChromosome getRandomChromosome();

    abstract protected void sortPopulation(List<BinaryChromosome> population);

    abstract protected double getFitness(BinaryChromosome chromosome);

    abstract protected String getPhenotypeRepresentation(BinaryChromosome chromosome);

    private List<BinaryChromosome> getInitialPopulation() {
        List<BinaryChromosome> chromosomeList = new ArrayList<>();
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            BinaryChromosome newChromosome = getRandomChromosome();
            chromosomeList.add(newChromosome);
        }
        return chromosomeList;
    }

    public Solution evaluatePopulation(List<BinaryChromosome> population) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return solution;
        }

        double totalFitness = 0;
        for (BinaryChromosome chromosome : population) {
            double fitness = getFitness(chromosome);
            chromosome.setFitness(fitness);
            totalFitness += fitness;
        }

        sortPopulation(population);
        BinaryChromosome bestChromosome = population.get(population.size()-1);
        solution.setAverageFitness(totalFitness/configuration.getPopulationSize());
        solution.setBestFitness(bestChromosome.getFitness());
        solution.setWorstFitness(population.get(0).getFitness());
        solution.setAbsoluteBest(bestChromosome.getFitness());

        String absoluteBestRepresentation = String.format("f(X) = %s \n Fitness = %.4f", getPhenotypeRepresentation(bestChromosome), bestChromosome.getFitness());
        solution.setAbsoluteBestRepresentation(absoluteBestRepresentation);

        double acumulatedFitness = 0;
        //Calculamos el fitness acumulado - Muestreo estocástico - Tema02 - Pág 36
        //Puede cambiar para mínimos :s
        for (int i = population.size()-1; i >= 0; i--) {
            acumulatedFitness += population.get(i).getFitness()/totalFitness;
            population.get(i).setAcumulatedFitness(acumulatedFitness);
        }

        return solution;
    }

    private void crossPopulation(List<BinaryChromosome> population) {
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
            int position2 = selectedForCrossoverList.get(i + 1);

            BinaryChromosome chromosomeA = population.get(position1);
            BinaryChromosome chromosomeB = population.get(position2);
            Pair<BinaryChromosome, BinaryChromosome> result = binaryCrossoverAlgorithm.crossOver(chromosomeA, chromosomeB);
            population.set(position1, result.getElement0());
            population.set(position2, result.getElement1());
        }
    }

    private void mutatePopulation(List<BinaryChromosome> population) {
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            BinaryChromosome chromosome = population.get(i);
            mutationAlgorithm.mutate(chromosome, configuration.getMutationValue());
            population.set(i, chromosome);
        }
    }

    private List<BinaryChromosome> getElite(List<BinaryChromosome> population) {
        int eliteLength = (int) Math.ceil(population.size() * configuration.getEliteValue());
        if (eliteLength == 0) {
            return null;
        }

        sortPopulation(population);
        List<BinaryChromosome> eliteList = new ArrayList<>(eliteLength);
        for (int i = (population.size() - 1), j = 0; i >= 0 && j < eliteLength; i--, j++) {
            BinaryChromosome newCopy = population.get(i).getCopy();
            eliteList.add(newCopy);
        }

        return eliteList;
    }

    private void addElite(List<BinaryChromosome> population, List<BinaryChromosome> eliteList) {
        if (eliteList == null) {
            return;
        }

        population.subList(0, eliteList.size()).clear();
        population.addAll(eliteList);
    }
}
