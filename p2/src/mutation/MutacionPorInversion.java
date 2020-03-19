package mutation;

import entities.PathChromosome;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Solo muta si al otener un número al azar este es menor que la probabilidad de mutación.
 */
public class MutacionPorInversion implements MutationAlgorithm {
    @Override
    public PathChromosome mutate(PathChromosome chromosome, double mutationProbability) {
        Random random = new Random();
        double result = random.nextDouble();
        if (result > mutationProbability) {
            return chromosome;
        }

        List<Integer> newGenes = chromosome.getGenes();

        int initialPoint = 0;
        int endPoint = 0;

        while (initialPoint >= endPoint) {
            initialPoint = random.nextInt(chromosome.getGenes().size()-1);
            endPoint = random.nextInt(chromosome.getGenes().size()-1);
        }

        for (int i = initialPoint, j = endPoint; i < endPoint; i++, j--) {
            Collections.swap(newGenes, i, j);
        }

        return new PathChromosome(newGenes);
    }
}
