package mutation;

import entities.PathChromosome;
import helper.Pair;
import helper.Utils;
import java.util.*;

/**
 * Elegimos 2 puntos al azar e intercambiamos los elemenos que hay en el segmento entre los dos puntos.
 */
public class MutacionPorInversion implements MutationAlgorithm {
    @Override
    public PathChromosome mutate(PathChromosome chromosome) {
        List<Integer> newGenes = chromosome.cloneGenotype();

        Pair<Integer, Integer> maxMinPair = Utils.getMaxMinPosition(chromosome.getGenes().size()-1);
        int initialPoint = maxMinPair.getElement0();
        int endPoint = maxMinPair.getElement1();

        for (int i = initialPoint, j = endPoint; i < endPoint; i++, j--) {
            Collections.swap(newGenes, i, j);
        }

        return new PathChromosome(newGenes);
    }
}
