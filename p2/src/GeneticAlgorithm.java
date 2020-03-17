import crossover.CrossoverAlgorithm;
import entities.Configuration;
import entities.PathChromosome;
import entities.Solution;
import helper.Pair;
import mutation.MutationAlgorithm;
import selection.SelectionAlgorithm;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneticAlgorithm extends Thread  {
    private Configuration configuration;
    private SelectionAlgorithm selectionAlgorithm;
    private CrossoverAlgorithm crossoverAlgorithm;
    private MutationAlgorithm mutationAlgorithm;
    private final Random random = new Random();
    private int[][] flujoMatrix;
    private int[][] distanciaMatrix;

    public GeneticAlgorithm(Configuration configuration, SelectionAlgorithm selectionAlgorithm, CrossoverAlgorithm crossoverAlgorithm, int[][] flujoMatrix, int[][] distanciaMatrix) {
        this.configuration = configuration;
        this.selectionAlgorithm = selectionAlgorithm;
        this.crossoverAlgorithm = crossoverAlgorithm;
        this.flujoMatrix = flujoMatrix;
        this.distanciaMatrix = distanciaMatrix;
    }

    @Override
    public void run() {
        super.run();

        List<PathChromosome> population = getInitialPopulation();
        List<Solution> solutions = new ArrayList<>();
        Solution solution = evaluatePopulation(population);
        solution.setAbsoluteBest(solution.getBestFitness());
        solutions.add(solution);
//        delegate.didEvaluateGeneration(0, solution);
        double absBest = solution.getAbsoluteBest();
        for (int i = 1; i < configuration.getNumberOfGenerations(); i++) {
            List<PathChromosome> eliteList = getElite(population);
            population = selectionAlgorithm.selectPopulation(population);
            crossPopulation(population);
//            mutatePopulation(population);
            addElite(population, eliteList);
            solution = evaluatePopulation(population);
            if (!isBetterFitness(solution.getAbsoluteBest(), absBest)) {
                solution.setAbsoluteBest(absBest);
            }
            absBest = solution.getAbsoluteBest();
            solutions.add(solution);

//            delegate.didEvaluateGeneration(i, solution);
        }

        int t = 2;
        t += 2;
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
        return  population;
    }

    private Solution evaluatePopulation(List<PathChromosome> population) {
        Solution solution = new Solution();

        if (population.size() == 0) {
            return solution;
        }

        double totalFitness = 0;
        for (PathChromosome chromosome : population) {
            double fitness = getFitness(chromosome);
            chromosome.setFitness(fitness);
            totalFitness += fitness;
        }

        sortPopulation(population);
        PathChromosome bestChromosome = population.get(population.size()-1);
        solution.setAverageFitness(totalFitness/configuration.getPopulationSize());
        solution.setBestFitness(bestChromosome.getFitness());
        solution.setWorstFitness(population.get(0).getFitness());
        solution.setAbsoluteBest(bestChromosome.getFitness());

        String absoluteBestRepresentation = String.format("Permutación %s", bestChromosome.getGenes().toString());
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

    private void sortPopulation(List<PathChromosome> population) {
        Collections.sort(population);
    }

    private double getFitness(PathChromosome chromosome) {
        double fitnes = 0;

        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            for (int j = 0; j < chromosome.getGenes().size(); j++) {
                fitnes += distanciaMatrix[i][j] * flujoMatrix[chromosome.getGenes().get(i)][chromosome.getGenes().get(j)];
            }
        }

        return fitnes;
    }

    private void crossPopulation(List<PathChromosome> population) {
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
    }


    private void mutatePopulation(List<PathChromosome> population) {
//        for (int i = 0; i < configuration.getPopulationSize(); i++) {
//            PathChromosome chromosome = population.get(i);
//            mutationAlgorithm.mutate(chromosome, configuration.getMutationValue());
//            population.set(i, chromosome);
//        }
    }

    private boolean isBetterFitness(double absoluteBest, double absBest) {
        return absoluteBest < absBest;
    }

    private List<PathChromosome> getElite(List<PathChromosome> population) {
        int eliteLength = (int) Math.ceil(population.size() * configuration.getEliteValue());
        if (eliteLength == 0) {
            return null;
        }

        sortPopulation(population);
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
