package selection;

import entities.BinaryChromosome;
import entities.Chromosome;

import java.util.List;

public interface SelectionAlgorithm<T extends Chromosome> {
	public List<T> selectPopulation(List<T> population);
}
