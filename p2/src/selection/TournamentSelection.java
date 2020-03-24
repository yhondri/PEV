package selection;

import entities.PathChromosome;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TournamentSelection implements SelectionAlgorithm {
	@Override
	public List selectPopulation(List population) {
		Random rng = new Random();
		int popSize = population.size();
		List<PathChromosome> selectedPopulation = new ArrayList<>();
		PathChromosome selected;
		for (int i = 0; i < popSize; i++) {
			selected = getBestChromosomeFromPopulation(new PathChromosome[]{
					(PathChromosome) population.get(rng.nextInt(popSize)),
					(PathChromosome) population.get(rng.nextInt(popSize)),
					(PathChromosome) population.get(rng.nextInt(popSize))});
			selectedPopulation.add(selected);
		}
        return selectedPopulation;
    }

	private PathChromosome getBestChromosomeFromPopulation(PathChromosome[] population) {
		int best = 0;
		double bestFitness = population[0].getFitness();
		for (int j = 1; j < 3; j++) {
			if (population[j].getFitness() > bestFitness) {
				best = j;
				bestFitness = population[j].getFitness();
			}
		}
		return population[best];
	}
}
