package selection;

import entities.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentSelection implements SelectionAlgorithm {
    @Override
    public List<Chromosome> selectPopulation(List<Chromosome> population) {
        Random rng = new Random();
        int popSize = population.size();
        List<Chromosome> selectedPopulation = new ArrayList<>();
        Chromosome selected;
        for (int i = 0; i < popSize; i++) {
            selected = getChromosomeFromPopulation(new Chromosome[]{
                    population.get(rng.nextInt(popSize)),
                    population.get(rng.nextInt(popSize)),
                    population.get(rng.nextInt(popSize))});
            selectedPopulation.add(selected);
        }
        return selectedPopulation;
    }

    private Chromosome getChromosomeFromPopulation(Chromosome[] population) {
        int best = 0;
        double bestFitness = population[0].getFitness();
        for (int j = 1; j < 3; j++) {
            if (population[j].getFitness() > bestFitness) {
                best = j;
                bestFitness = population[j].getFitness();
            }
        }
        return population[best];
    }
}
