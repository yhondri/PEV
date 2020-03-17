package crossover;

import entities.PathChromosome;
import helper.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SinglePointBinaryCrossover implements CrossoverAlgorithm {
	private Random random;

	public SinglePointBinaryCrossover(Random random) {
		this.random = random;
	}

	@Override
	public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
		int chromosomeSize = Math.max(chromosomeA.getGenes().size(), chromosomeB.getGenes().size());

		int crossoverPoint = random.nextInt(chromosomeA.getGenes().size());

		List<Integer> chromosomeANew = new ArrayList<>();
        List<Integer> chromosomeBNew = new ArrayList<>();

		for (int i = 0; i < crossoverPoint; i++) {
			if (i < chromosomeANew.size() && i < chromosomeA.getGenes().size()) {
				chromosomeANew.set(i, chromosomeA.getGenes().get(i));
            }

            if (i < chromosomeBNew.size() && i < chromosomeB.getGenes().size()) {
                chromosomeBNew.set(i, chromosomeB.getGenes().get(i));
            }
        }

        for (int i = crossoverPoint; i < chromosomeSize; i++) {
            if (i < chromosomeANew.size() && i < chromosomeB.getGenes().size()) {
                chromosomeANew.set(i, chromosomeB.getGenes().get(i));
            }

            if (i < chromosomeBNew.size() && i < chromosomeA.getGenes().size()) {
                chromosomeBNew.set(i, chromosomeA.getGenes().get(i));
            }
        }

		return new Pair<>(new PathChromosome(chromosomeANew), new PathChromosome(chromosomeBNew));
    }
}
