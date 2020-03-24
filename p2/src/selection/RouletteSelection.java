package selection;

import entities.PathChromosome;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteSelection implements SelectionAlgorithm {
	@Override
	public List selectPopulation(List population) {
		List<PathChromosome> selectedPopulation = new ArrayList<>();

		for (int i = 0; i < population.size(); i++) {
			double trialValue = Utils.random.nextDouble();
			PathChromosome selectedChromosome = getChromosomeFromPopulation(population, trialValue);
			selectedPopulation.add(selectedChromosome);
		}

		return selectedPopulation;
    }

	//Tenemos que recorrer desde el final hacía el inicio de la lista.
	private PathChromosome getChromosomeFromPopulation(List<PathChromosome> population, double trial) {
		int index = population.size() - 1;
		while (index >= 0 && trial > population.get(index).getAcumulatedFitness()) {
			index--;
		}
		return population.get(index).getCopy();
	}
}