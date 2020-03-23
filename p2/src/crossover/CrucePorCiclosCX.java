package crossover;

import entities.PathChromosome;
import helper.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrucePorCiclosCX implements CrossoverAlgorithm {
    @Override
    public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
        List<Integer> genesA = getGenes(chromosomeA.getGenes(), chromosomeB.getGenes());
        List<Integer> genesB = getGenes(chromosomeB.getGenes(), chromosomeA.getGenes());
        return new Pair<>(new PathChromosome(genesA), new PathChromosome(genesB));
    }

    private List<Integer> getGenes(List<Integer> parentA, List<Integer> parentB) {
        List<Integer> newGenes = new ArrayList<>(Collections.nCopies(parentA.size(), null));

        boolean end = false;
        int index = 0;
        int indexOfNextValue;
        Integer valueToInsert = parentA.get(index);
        newGenes.set(index, valueToInsert);
        indexOfNextValue = index;
        while (!end && indexOfNextValue < parentA.size()) {
            valueToInsert =  parentB.get(indexOfNextValue);
            indexOfNextValue = parentA.indexOf(valueToInsert);
            if (!newGenes.contains(valueToInsert)) {
                newGenes.set(indexOfNextValue, valueToInsert);
            } else {
                end = true;
            }
        }

        for (int i = 0; i < newGenes.size(); i++) {
            if (newGenes.get(i) == null) {
                newGenes.set(i, parentB.get(i));
            }
        }

        return newGenes;
    }
}
