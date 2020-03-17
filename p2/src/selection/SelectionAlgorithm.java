package selection;

import entities.PathChromosome;
import java.util.List;

public interface SelectionAlgorithm<T extends PathChromosome> {
	public List<T> selectPopulation(List<T> population);
}
