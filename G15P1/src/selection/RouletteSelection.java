package selection;

import entities.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteSelection implements SelectionAlgorithm {
    public List<Chromosome> selectPopulation(List<Chromosome> population) {
        Random random = new Random();
        List<Chromosome> selectedPopulation = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            double trialValue = random.nextDouble();
            Chromosome selectedChromosome = getChromosomeFromPopulation(population, trialValue);
            selectedPopulation.add(selectedChromosome);
        }

        return selectedPopulation;
    }

    private Chromosome getChromosomeFromPopulation(List<Chromosome> population, double trial) {
        int index = 0;
        int maxIndex = population.size()-1;
        while (index < maxIndex && trial > population.get(index).getAcumulatedFitness()) {
            index++;
        }
        return population.get(index);
    }
}