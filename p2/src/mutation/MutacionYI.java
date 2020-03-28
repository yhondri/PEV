package mutation;

import entities.PathChromosome;
import helper.Utils;
import java.util.Collections;
import java.util.List;

/**
 * Esta mutación intercambia n posiciones elegidas al azar de los genes del padre.
 */
public class MutacionYI implements MutationAlgorithm {
    @Override
    public PathChromosome mutate(PathChromosome chromosome) {
        int numberOfPositionsToMutate = Utils.getRandom(chromosome.getGenes().size()-1, 0); //Obtenemos el número de posiciones a mutar.
        List<Integer> newGenes = chromosome.cloneGenotype();
        while (numberOfPositionsToMutate > 0) {
            int i = Utils.getRandom(chromosome.getGenes().size()-1, 0);
            int j = Utils.getRandom(chromosome.getGenes().size()-1, 0);
            Collections.swap(newGenes, i, j);
            numberOfPositionsToMutate -= 1;
        }

        return new PathChromosome(newGenes);
    }
}
