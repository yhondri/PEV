package selection;

import entities.PathChromosome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteSelection implements SelectionAlgorithm {
	//Tenemos que recorrer desde el final hacía el inicio de la lista.
	private PathChromosome getChromosomeFromPopulation(List<PathChromosome> population, double trial) {
		int index = population.size() - 1;
		while (index >= 0 && trial > population.get(index).getAcumulatedFitness()) {
			index--;
		}
		return population.get(index);
	}

	@Override
	public List selectPopulation(List population) {
		Random random = new Random();
		List<PathChromosome> selectedPopulation = new ArrayList<>();

		for (int i = 0; i < population.size(); i++) {
			double trialValue = random.nextDouble();
			PathChromosome selectedChromosome = getChromosomeFromPopulation(population, trialValue);
			selectedPopulation.add(selectedChromosome);
		}

		return selectedPopulation;
    }
}