package selection;

import entities.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TruncationSelection implements SelectionAlgorithm {
    private static final double proportion = 0.5;

    @Override
    public List<Chromosome> selectPopulation(List<Chromosome> population) {
        int numClones = (int) (1 / proportion), count = 0;
        Collections.sort(population);
        List<Chromosome> selectedPopulation = new ArrayList<>();
        for (int i = 0; i < population.size() * proportion && count < population.size(); i++) {
            for (int j = 0; j < numClones && count < population.size(); j++) {
                selectedPopulation.add(population.get(i));
            }
        }
        return selectedPopulation;
    }
}
