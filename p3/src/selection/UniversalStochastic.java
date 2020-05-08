package selection;

import entities.TreeNode;
import helper.Utils;

import java.util.ArrayList;
import java.util.List;

public class UniversalStochastic implements SelectionAlgorithm {
	@Override
	public List selectPopulation(List population) {
		List<TreeNode> selectedPopulation = new ArrayList<>();
		double interval = ((double) 1 / population.size()), trialValue = Utils.random.nextDouble() / population.size();
		for (int i = 0; i < population.size(); i++) {
			TreeNode selectedChromosome = getChromosomeFromPopulation(population, trialValue);
			selectedPopulation.add(selectedChromosome);
			trialValue += interval;
		}

		return selectedPopulation;
	}

	private TreeNode getChromosomeFromPopulation(List<TreeNode> population, double trial) {
		int index = population.size() - 1;
		while (index >= 0 && trial > population.get(index).getAcumulatedFitness()) {
			index--;
		}
		return population.get(index).getCopy();
	}

}
