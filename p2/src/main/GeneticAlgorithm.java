package main;

import crossover.CrossoverAlgorithm;
import entities.Configuration;
import entities.PathChromosome;
import entities.Solution;
import helper.Pair;
import helper.Utils;
import mutation.MutationAlgorithm;
import selection.SelectionAlgorithm;
import java.util.*;

public class GeneticAlgorithm extends Thread {
    private Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private CrossoverAlgorithm crossoverAlgorithm;
    private MutationAlgorithm mutationAlgorithm;
    private final Random random = new Random();
    private FitnessCalculator fitnessCalculator;
    private final GeneticAlgorithmDelegate delegate;

    public GeneticAlgorithm(Configuration configuration, SelectionAlgorithm selectionAlgorithm, CrossoverAlgorithm crossoverAlgorithm, MutationAlgorithm mutationAlgorithm, FitnessCalculator fitnessCalculator, GeneticAlgorithmDelegate delegate) {
        this.configuration = configuration;
        this.selectionAlgorithm = selectionAlgorithm;
        this.crossoverAlgorithm = crossoverAlgorithm;
        this.mutationAlgorithm = mutationAlgorithm;
        this.fitnessCalculator = fitnessCalculator;
        this.delegate = delegate;
    }

//    private void checkDuplicates(List<PathChromosome> population) {
//        for (PathChromosome pathChromosome : population) {
//            Set<Integer> set = new HashSet<>(pathChromosome.getGenes());
//            if (set.size() < pathChromosome.getGenes().size()) {
//                System.out.println("Stop, hay duplicados");
//            }
//        }
//    }

//        private void containsSix(List<PathChromosome> population) {
//        for (PathChromosome pathChromosome : population) {
//            if (pathChromosome.getGenes().contains(6)) {
//                System.out.println("Stop, contiene 6");
//            }
//        }
//    }

//    private void checkNulls(List<PathChromosome> population) {
//        for (PathChromosome pathChromosome: population) {
//            for (Integer value: pathChromosome.getGenes()) {
//                if (value == null) {
//                    System.out.println("Stop");
//                }
//            }
//        }
//    }


    @Override
    public void run() {
        super.run();

        delegate.areButtonsEnabled(false);
        delegate.onStarSearch();

        List<PathChromosome> population = getInitialPopulation();
        List<Solution> solutions = new ArrayList<>();
        Solution solution = evaluatePopulation(population, 0, 0);
        solution.setAbsoluteBest(solution.getBestFitness());
        solutions.add(solution);
        delegate.didEvaluateGeneration(0, solution);

        double absBest = solution.getAbsoluteBest();
        for (int i = 1; i < configuration.getNumberOfGenerations(); i++) {
            List<PathChromosome> eliteList = getElite(population);
            population = selectionAlgorithm.selectPopulation(population);
            int numberOfcrossovers = crossPopulation(population);
            int numberOfMutations = mutatePopulation(population);
            addElite(population, eliteList);
            solution = evaluatePopulation(population, numberOfcrossovers, numberOfMutations);
            if (!isBetterFitness(solution.getAbsoluteBest(), absBest)) {
                solution.setAbsoluteBest(absBest);
            }
            absBest = solution.getAbsoluteBest();
            solutions.add(solution);
            delegate.didEvaluateGeneration(i, solution);
        }

        delegate.onEndSearch(solution);
        delegate.areButtonsEnabled(true);
    }

    private List<PathChromosome> getInitialPopulation() {
        List<PathChromosome> population = new ArrayList<>();
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            List<Integer> genes = new ArrayList<>();
            for (int j = 0; j < configuration.getChromosomeSize(); j++) {
                genes.add(j);
            }

            for (int j = 0; j < genes.size(); j++) {
                int randomPosition = random.nextInt(genes.size()-1);
                Collections.swap(genes, j, randomPosition);
            }

            population.add(new PathChromosome(genes));
        }

        return population;
    }

    private Solution evaluatePopulation(List<PathChromosome> population, int numberOfCrossover, int numberOfMutations) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return solution;
        }

        double totalFitness = 0;
        for (PathChromosome chromosome : population) {
            double fitness = fitnessCalculator.getFitness(chromosome);
            chromosome.setFitness(fitness);
            totalFitness += fitness;
        }

        population = sortPopulation(population);
        PathChromosome bestChromosome = population.get(population.size()-1);
        solution.setAverageFitness(totalFitness/configuration.getPopulationSize());
        solution.setBestFitness(bestChromosome.getFitness());
        solution.setWorstFitness(population.get(0).getFitness());
        solution.setAbsoluteBest(bestChromosome.getFitness());

        String absoluteBestRepresentation = String.format(" Fitnes óptimo del problema: %s\n " +
                        "Mejor fitness enontrado: %.2f\n " +
                        "Permutación: %s\n " +
                        "Peor fitness: %.2f\n " +
                        "Media: %.2f\n " +
                        "Número de cruces: %d\n " +
                        "Número de mutaciones: %d",
                configuration.getCosteOptimo(),
                bestChromosome.getFitness(),
                bestChromosome.getGenes().toString(),
                solution.getWorstFitness(),
                solution.getAverageFitness(),
                numberOfCrossover,
                numberOfMutations);
        solution.setAbsoluteBestRepresentation(absoluteBestRepresentation);

        double acumulatedFitness = 0;
        //Calculamos el fitness acumulado - Muestreo estocástico - Tema02 - Pág 36
        //Puede cambiar para mínimos :s
        for (int i = population.size()-1; i >= 0; i--) {
            acumulatedFitness += population.get(i).getFitness()/totalFitness;
            population.get(i).setAcumulatedFitness(acumulatedFitness);
            population.get(i).setGrade(population.get(i).getFitness() / totalFitness);
        }

        return solution;
    }

    private List<PathChromosome> sortPopulation(List<PathChromosome> population) {
        Collections.sort(population, Collections.reverseOrder());
        return population;
    }

    /**
     * Cruza los individuos de una poblacción para producir individuos que combinan característiccas de los progenitores.
     * @param population Los individuos a cruzar.
     * @return Devuelve el número de curces que se han producido.
     */
    private int crossPopulation(List<PathChromosome> population) {
        List<Integer> selectedForCrossoverList = new ArrayList<>();
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            double crossoverResult = random.nextDouble();
            if (crossoverResult < configuration.getCrossoverValue()) {
                selectedForCrossoverList.add(i);
            }
        }

        if (selectedForCrossoverList.size()%2 == 1) {
            int randomPosition = random.nextInt(selectedForCrossoverList.size());
            selectedForCrossoverList.remove(randomPosition);
        }

        for (int i = 0; i < selectedForCrossoverList.size(); i += 2) {
            int position1 = selectedForCrossoverList.get(i);
            int position2 = selectedForCrossoverList.get(i + 1);

            PathChromosome chromosomeA = population.get(position1);
            PathChromosome chromosomeB = population.get(position2);

            Pair<PathChromosome, PathChromosome> result = crossoverAlgorithm.crossOver(chromosomeA, chromosomeB);

            population.set(position1, result.getElement0());
            population.set(position2, result.getElement1());
        }

        return selectedForCrossoverList.size();
    }



    /**
     * Se aplica una pequeña modificcacción en uno o mas genes de un individuo.
     * @param population Los individuos a mutar.
     * @return Devuelve el número de mutaciones que se han producido.
     */
    private int mutatePopulation(List<PathChromosome> population) {
        int numberOfMutation = 0;
        for (int i = 0; i < configuration.getPopulationSize(); i++) {
            PathChromosome chromosome;
            double result = Utils.random.nextDouble();
            if (result > configuration.getMutationValue()) {
                chromosome =  population.get(i).getCopy();
            } else {
                chromosome = mutationAlgorithm.mutate(population.get(i));
                numberOfMutation += 1;
            }

            population.set(i, chromosome);
        }

        return numberOfMutation;
    }

    private boolean isBetterFitness(double absoluteBest, double absBest) {
        return absoluteBest <= absBest;
    }

    private List<PathChromosome> getElite(List<PathChromosome> population) {
        int eliteLength = (int) Math.ceil(population.size() * configuration.getEliteValue());
        if (eliteLength == 0) {
            return null;
        }

        population = sortPopulation(population);
        List<PathChromosome> eliteList = new ArrayList<>(eliteLength);
        for (int i = (population.size() - 1), j = 0; i >= 0 && j < eliteLength; i--, j++) {
            PathChromosome newCopy = population.get(i).getCopy();
            eliteList.add(newCopy);
        }

        return eliteList;
    }

    private void addElite(List<PathChromosome> population, List<PathChromosome> eliteList) {
        if (eliteList == null) {
            return;
        }

        population.subList(0, eliteList.size()).clear();
        population.addAll(eliteList);
    }
}
