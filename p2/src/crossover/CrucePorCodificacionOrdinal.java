package crossover;

import entities.PathChromosome;
import helper.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CrucePorCodificacionOrdinal implements CrossoverAlgorithm {
    @Override
    public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
        List<Integer> encodedA = encode(chromosomeA.getGenes());
        List<Integer> encodedB = encode(chromosomeB.getGenes());

        Pair<List<Integer>, List<Integer>> crossoverResult = getSinglePointCrossover(encodedA, encodedB);
        List<Integer> genesA = decode(crossoverResult.getElement0());
        List<Integer> genesB = decode(crossoverResult.getElement1());

        return new Pair<>(new PathChromosome(genesA), new PathChromosome(genesB));
    }

    public Pair<List<Integer>, List<Integer>> getSinglePointCrossover(List<Integer> chromosomeA, List<Integer> chromosomeB) {
        int chromosomeSize = Math.max(chromosomeA.size(), chromosomeB.size());
        Random random = new Random();
        int crossoverPoint = random.nextInt(chromosomeA.size()-1);

        List<Integer> chromosomeANew = new ArrayList<>(Collections.nCopies(chromosomeA.size(), null));
        List<Integer> chromosomeBNew = new ArrayList<>(Collections.nCopies(chromosomeA.size(), null));

        for (int i = 0; i < crossoverPoint; i++) {
            if (i < chromosomeANew.size() && i < chromosomeA.size()) {
                chromosomeANew.set(i, chromosomeA.get(i));
            }

            if (i < chromosomeBNew.size() && i < chromosomeB.size()) {
                chromosomeBNew.set(i, chromosomeB.get(i));
            }
        }

        for (int i = crossoverPoint; i < chromosomeSize; i++) {
            if (i < chromosomeANew.size() && i < chromosomeB.size()) {
                chromosomeANew.set(i, chromosomeB.get(i));
            }

            if (i < chromosomeBNew.size() && i < chromosomeA.size()) {
                chromosomeBNew.set(i, chromosomeA.get(i));
            }
        }

        return new Pair<>(chromosomeANew, chromosomeBNew);
    }

    private List<Integer> encode(List<Integer> parent) {
        List<Integer> newGenes = new ArrayList<>();
        List<Integer> dinamicList = new ArrayList<>();
        for (int i = 0; i < parent.size(); i++) {
            dinamicList.add(new Integer(i));
        }

        for (int i = 0; i < parent.size(); i++) {
            Integer value = parent.get(i);
            int newValue = dinamicList.indexOf(value);
            newGenes.add(newValue);
            dinamicList.remove(new Integer(value));
        }

        return newGenes;
    }

    private List<Integer> decode(List<Integer> parent) {
        List<Integer> newGenes = new ArrayList<>();
        List<Integer> dinamicList = new ArrayList<>();
        for (int i = 0; i < parent.size(); i++) {
            dinamicList.add(new Integer(i));
        }

        for (int i = 0; i < parent.size(); i++) {
            Integer value = parent.get(i);
            int newValue = dinamicList.get(value);
            newGenes.add(newValue);
            dinamicList.remove((int)value);
        }

        return newGenes;
    }
}
