package selection;

import entities.PathChromosome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RankingSelection implements SelectionAlgorithm {
	private static final double factorMuestreo = 1.5;

	@Override
	public List selectPopulation(List population) {
		List<PathChromosome> selectedPopulation = new ArrayList<>();
		double[] fitnessSegment = rankPops(population);
		Random rd = new Random();
		int numParents = population.size(), count = 0;
		while (count < numParents) {
			double x = rd.nextDouble() * fitnessSegment[population.size() - 1];
			if (x <= fitnessSegment[0]) {
				selectedPopulation.add((PathChromosome) population.get(numParents - 1));
				count++;
			} else {
				for (int i = 1; i < numParents; i++) {
					if (x > fitnessSegment[i - 1] && x <= fitnessSegment[i]) {
						selectedPopulation.add((PathChromosome) population.get(numParents - 1 - i));
						count++;
					}
				}
			}
		}
		return selectedPopulation;
	}

	private double[] rankPops(List<PathChromosome> population) {
		double[] ret = new double[population.size()];
		double prob;
		for (int i = 0; i < population.size(); i++) {
			prob = i / population.size();
			prob *= 2 * (factorMuestreo - 1);
			prob = factorMuestreo - prob;
			prob /= population.size();
			if (i != 0) {
				ret[i] = ret[i - 1] + prob;
			} else {
				ret[i] = prob;
			}
		}
		return ret;
	}
}
