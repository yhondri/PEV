package crossover;

import entities.PathChromosome;
import helper.Pair;
import helper.Utils;
import java.util.ArrayList;
import java.util.List;

/**
 * Cruce los genes de los dos padres de forma aleatoria para crear el nuevo individuo.
 */
public class CruceYI  implements CrossoverAlgorithm {
    @Override
    public Pair<PathChromosome, PathChromosome> crossOver(PathChromosome chromosomeA, PathChromosome chromosomeB) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < chromosomeA.getGenes().size(); i++) {
            data.add(chromosomeA.getGenes().get(i));
            data.add(chromosomeB.getGenes().get(i));
        }

        List<Integer> genesA = performeCrossOver(data, chromosomeA.getGenes().size());
        List<Integer> genesB = performeCrossOver(data, chromosomeA.getGenes().size());
        return new Pair<>(new PathChromosome(genesA), new PathChromosome(genesB));
    }

    private List<Integer> performeCrossOver(List<Integer> genesList, int genesSize) {
        List<Integer> newGenes = new ArrayList<>();

        while(newGenes.size() != genesSize) {
            int randomPosition = Utils.getRandom(genesList.size()-1, 0); //Obtenemos la posición a partir de la cual copiaremos los genes del padre B.
            if (newGenes.contains(genesList.get(randomPosition))) {
                continue;
            }

            newGenes.add(genesList.get(randomPosition));
        }

        return newGenes;
    }
}
