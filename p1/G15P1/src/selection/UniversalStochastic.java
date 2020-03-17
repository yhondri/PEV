package selection;

import entities.BinaryChromosome;
import entities.Chromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UniversalStochastic implements SelectionAlgorithm {
	@Override
	public List selectPopulation(List population) {
		double markSeparation = 1 / (double) population.size();
		Random rd = new Random();
		double trialValue = rd.nextDouble() * markSeparation;
		List<Chromosome> selectedPopulation = new ArrayList<>();
		Chromosome selectedChromosome;
		for (int i = 0; i < population.size() && trialValue < 1; i++) {
			selectedChromosome = getChromosomeFromPopulation(population, trialValue);
			selectedPopulation.add(selectedChromosome);
			trialValue += markSeparation;
		}

        return selectedPopulation;
    }

    private Chromosome getChromosomeFromPopulation(List<Chromosome> population, double trial) {
        int index = population.size() - 1;
        while (index >= 0 && trial > population.get(index).getAcumulatedFitness()) {
            index--;
        }
        return population.get(index);
    }
}
