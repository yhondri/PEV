package selection;

import entities.TreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentSelection implements SelectionAlgorithm {
	@Override
	public List selectPopulation(List population) {
		Random rng = new Random();
		int popSize = population.size();
		List<TreeNode> selectedPopulation = new ArrayList<>();
		TreeNode selected;
		for (int i = 0; i < popSize; i++) {
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
}
