package mutation;

import entities.PathChromosome;
import helper.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Elegimos 2 puntos al azar e intercambiamos los valores de las posiciones seleccionadas.
 */
public class MutacionPorIntercambio implements MutationAlgorithm {
    @Override
    public PathChromosome mutate(PathChromosome chromosome) {
        List<Integer> newGenes = chromosome.cloneGenotype();

        int initialPoint = 0;
        int endPoint = 0;


        while (initialPoint >= endPoint) {
            initialPoint = Utils.getRandom(chromosome.getGenes().size()-1, 0);
            endPoint =  Utils.getRandom(chromosome.getGenes().size()-1, 0);
        }

        Collections.swap(newGenes, initialPoint, endPoint);

        return new PathChromosome(newGenes);
    }
}
