package selection;

import entities.Chromosome;

import java.util.List;

public interface SelectionAlgorithm {
    public List<Chromosome> selectPopulation(List<Chromosome> population);
}
