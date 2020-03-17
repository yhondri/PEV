package problems;

import entities.BinaryChromosome;

public interface GenericProblem {
	public double getFitness(BinaryChromosome chromosome);

	BinaryChromosome getRandomChromosome();
}
