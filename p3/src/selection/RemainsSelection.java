package selection;

import entities.TreeNode;
import entities.TreeNode;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RemainsSelection implements SelectionAlgorithm {
	@Override
	public List selectPopulation(List population) {
		return getRemains(population);
	}

	private List<TreeNode> getRemains(List<TreeNode> population) {
		List<TreeNode> selectedPopulation = new ArrayList<>();
		TreeNode toCopy;
		int copies, initialPopSize = population.size();
		for (int i = initialPopSize - 1; i >= 0; i--) {
			toCopy = population.get(i);
			copies = (int) (toCopy.getGrade() * population.size());
			if (copies > 0) {
				population.remove(i);
				for (int j = 0; j < copies; j++) {
					selectedPopulation.add(toCopy);
				}
			}
		}
		selectedPopulation = append(selectedPopulation, selectFromRestByTournament(population, initialPopSize - selectedPopulation.size()));
		return selectedPopulation;
	}

	private List<TreeNode> selectFromRestByTournament(List<TreeNode> population, int maxSelections) {
		List<TreeNode> selectedPopulation = new ArrayList<>();
		TreeNode selected;
		Random rng = new Random();
		int popSize = population.size();
		for (int i = 0; i < maxSelections; i++) {
			selected = getBestChromosomeFromPopulation(new TreeNode[]{
					(TreeNode) population.get(rng.nextInt(popSize)),
					(TreeNode) population.get(rng.nextInt(popSize)),
					(TreeNode) population.get(rng.nextInt(popSize))});
			selectedPopulation.add(selected);
		}
		return selectedPopulation;
	}

	private TreeNode getBestChromosomeFromPopulation(TreeNode[] population) {
		int best = 0;
		TreeNode bestFitness = population[0];
		for (int j = 1; j < 3; j++) {
			if (population[j].compareTo(bestFitness) < 0) {
				best = j;
				bestFitness = population[j];
			}
		}
		return population[best];
	}

	private List<TreeNode> append(List<TreeNode> l1, List<TreeNode> l2) {
		List<TreeNode> ret = l1, iterate = l2;
		if (l1.size() < l2.size()) {
			ret = l2;
			iterate = l1;
		}
		for (TreeNode c : iterate) {
			l2.add(c);
		}
		return l2;
	}
}
