package selection;

import entities.TreeNode;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;

public class UniversalStochastic implements SelectionAlgorithm {
	@Override
	public List selectPopulation(List population) {
		List<TreeNode> selectedPopulation = new ArrayList<>();
		double interval =  1.0 / population.size();
		double trialValue = Utils.random.nextDouble() * interval;
		for (int i = 0; i < population.size(); i++) {
			TreeNode selectedChromosome = getChromosomeFromPopulation(population, trialValue);
			selectedPopulation.add(selectedChromosome);
			trialValue += interval;
		}

		return selectedPopulation;
	}

	private TreeNode getChromosomeFromPopulation(List<TreeNode> population, double trial) {
		int maxIndex = population.size() - 1;
		int index = 0;

		while (index < maxIndex && trial > population.get(index).getAcumulatedFitness()) {
			index++;
		}

		return population.get(index).getCopy();
	}

}
