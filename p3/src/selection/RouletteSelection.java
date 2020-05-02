package selection;

import entities.TreeNode;
import helper.Utils;
import java.util.ArrayList;
import java.util.List;

public class RouletteSelection implements SelectionAlgorithm {
	@Override
	public List<TreeNode> selectPopulation(List population) {
		List<TreeNode> selectedPopulation = new ArrayList<>();

		for (int i = 0; i < population.size(); i++) {
			double trialValue = Utils.random.nextDouble();
			TreeNode selectedChromosome = getChromosomeFromPopulation(population, trialValue);
			selectedPopulation.add(selectedChromosome);
		}

		return selectedPopulation;
    }

	//Tenemos que recorrer desde el final hacía el inicio de la lista.
	private TreeNode getChromosomeFromPopulation(List<TreeNode> population, double trial) {
		int index = population.size() - 1;
		while (index >= 0 && trial > population.get(index).getAcumulatedFitness()) {
			index--;
		}
		if (index == -1) {
			System.out.println("Stop " +index);
		}
		return population.get(index).getCopy();
	}
}