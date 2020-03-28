package selection;

import entities.PathChromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TruncationSelection implements SelectionAlgorithm {
	private static final double proportion = 0.4;

	@Override
	public List selectPopulation(List population) {
		int numClones = (int) (1 / proportion), count = 0;
		List selectedPopulation = new ArrayList<>();
		for (int i = population.size() - 1; i < population.size() * proportion && count < population.size(); i--) {
			for (int j = 0; j < numClones && count < population.size(); j++) {
				selectedPopulation.add(population.get(i));
			}
		}
		if (selectedPopulation.size() < population.size()) {
			for (int i = population.size() - 1; selectedPopulation.size() < population.size(); i--) {
				selectedPopulation.add(population.get(i));
			}
		}
		return selectedPopulation;
	}
}

