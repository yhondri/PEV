package selection;

import entities.PathChromosome;

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

	private List<PathChromosome> getRemains(List<PathChromosome> population) {
		List<PathChromosome> selectedPopulation = new ArrayList<>();
		PathChromosome toCopy;
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

	private List<PathChromosome> selectFromRestByTournament(List<PathChromosome> population, int maxSelections) {
		List<PathChromosome> selectedPopulation = new ArrayList<>();
		PathChromosome selected;
		Random rng = new Random();
		int popSize = population.size();
		for (int i = 0; i < maxSelections; i++) {
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
		PathChromosome bestFitness = population[0];
		for (int j = 1; j < 3; j++) {
			if (population[j].compareTo(bestFitness) < 0) {
				best = j;
				bestFitness = population[j];
			}
		}
		return population[best];
	}

	private List<PathChromosome> append(List<PathChromosome> l1, List<PathChromosome> l2) {
		List<PathChromosome> ret = l1, iterate = l2;
		if (l1.size() < l2.size()) {
			ret = l2;
			iterate = l1;
		}
		for (PathChromosome c : iterate) {
			l2.add(c);
		}
		return l2;
	}
}
