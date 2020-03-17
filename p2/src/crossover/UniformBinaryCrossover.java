package crossover;

import helper.Pair;
import entities.PathChromosome;
import java.util.List;
import java.util.Random;

public class UniformBinaryCrossover implements CrossoverAlgorithm {

	private double prob = 0.5;

	public UniformBinaryCrossover(double prob) {
		this.prob = prob;
	}

	@Override
	public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
		Random rng = new Random();
		Pair<PathChromosome, PathChromosome> descendants = new Pair<>(chromosomeA.getCopy(), chromosomeB.getCopy());
		List<Integer> genesA = chromosomeA.clonseGenes();
		List<Integer> genesB = chromosomeB.clonseGenes();
		Integer geneAtI;
		for (int i = 0; i < chromosomeA.getGenes().size(); i++) {
			if (rng.nextDouble() < prob) {
				geneAtI = genesA.get(i);
				genesA.set(i, genesB.get(i));
				genesB.set(i, geneAtI);
			}
        }
        descendants.getElement0().setGenes(genesA);
        descendants.getElement1().setGenes(genesB);
        return descendants;
    }
}
